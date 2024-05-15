/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartyflip.accounting.dao.UserRepository;
import smartyflip.accounting.dto.exceptions.UserNotFoundException;
import smartyflip.accounting.model.UserAccount;
import smartyflip.card.dao.CardRepository;
import smartyflip.card.service.exceptions.InvalidSubscriptionTypeException;
import smartyflip.card.service.exceptions.PayloadRequiredException;
import smartyflip.modules.dao.ModuleRepository;
import smartyflip.card.dao.TagRepository;
import smartyflip.modules.dto.DatePeriodDto;
import smartyflip.modules.dto.ModuleDto;
import smartyflip.modules.dto.NewModuleDto;
import smartyflip.modules.model.Module;
import smartyflip.modules.model.Tag;
import smartyflip.modules.service.exceptions.ModuleNotFoundException;
import smartyflip.modules.service.util.RegisteredModule;
import smartyflip.modules.service.util.TrialModule;
import smartyflip.stacks.dao.StackRepository;
import smartyflip.stacks.model.Stack;
import smartyflip.utils.PagedDataResponseDto;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    public static final String GUEST_TYPE = "GUEST";
    public static final String USER_TYPE = "USER";

    private final ModuleRepository moduleRepository;

    private final CardRepository cardRepository;

    private final StackRepository stackRepository;

    private final TagRepository tagRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    // TO REVISE

//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public Module getModule(Long moduleId, String token) {
//        if (!checkAccess(token)) {
//            throw new SecurityException("Access Denied: Invalid or expired token.");
//        }
//
//        Module module = moduleRepository.findById(moduleId).orElseThrow(() -> new IllegalArgumentException("Module not found"));
//
//        if (module instanceof TrialModule) {
//            TrialModule trialModule = (TrialModule) module;
//            if ( LocalDate.now().isAfter(trialModule.getTrialEndDate())) {
//                throw new RuntimeException("Trial period has expired.");
//            }
//        }
//
//        return module;
//    }
//
//    private boolean checkAccess(String token) {
//        // Assume tokenService.decodeToken returns an object that includes user role
//        DecodedTokenInfo tokenInfo = tokenService.decodeToken(token);
//        return tokenInfo.isValid() && (tokenInfo.getRole().equals("REGISTERED") || (tokenInfo.getRole().equals("GUEST") && module instanceof TrialModule));
//    }


    private Module findModuleOrThrow(Long moduleId) {
        return moduleRepository.findById(moduleId).orElseThrow(ModuleNotFoundException::new);
    }


    @Transactional
    @Override
    public ModuleDto addModule(NewModuleDto newModuleDto, String subscriptionType) {
        Optional<UserAccount> userAccount = userRepository.findByUsername(newModuleDto.getUserName());

        Module module;

        // Create a new Module

        if ( "GUEST".equalsIgnoreCase(subscriptionType) ) {
            module = modelMapper.map(newModuleDto, TrialModule.class);
            ((TrialModule) module).setSubscriptionType("GUEST");
        } else if ( "USER".equalsIgnoreCase(subscriptionType) ) {
            module = modelMapper.map(newModuleDto, RegisteredModule.class);
            ((RegisteredModule) module).setSubscriptionType("USER");
        } else {
            throw new IllegalArgumentException("Invalid subscription type.");
        }

        // Set up a userName
//        module.setUserName(newModuleDto.getUserName().trim().toLowerCase().replaceAll("\\s+", "_"));
        module.setUserName(userAccount.map(UserAccount::getUsername).orElseThrow(UserNotFoundException::new));


        // Check if the Stack already exists
        Stack stack = stackRepository.findByStackNameIgnoreCase(newModuleDto.getStackName());
        if ( stack == null ) {
            // If the Stack doesn't exist, create and save a new Stack
            stack = new Stack();
            stack.setStackName(newModuleDto.getStackName().trim().toUpperCase().replaceAll("\\s+", "_"));
            stack = stackRepository.save(stack);
        }

        // Update Module's Stack
        module.setStack(stack);

        // Adding tags Collection to Module
//        Collection<String> tagNames = newModuleDto.getTagNames();
//        List<Tag> tagsToAdd = tagNames.stream()
//                .map(this::insertIfNotExist)
//                .toList();
//
//        module.getTags().addAll(tagsToAdd); // Add all the fetched tags to the module
//        moduleRepository.save(module);

        // Create a date of module creation
        module.setDateCreated(LocalDateTime.now());
        // Save Module in repository
        module = moduleRepository.save(module);
        return modelMapper.map(module, ModuleDto.class);
    }

    @Transactional
    public Tag insertIfNotExist(String tagName) {
        Tag existingTag = tagRepository.findByTagName(tagName);
        if ( existingTag == null ) {
            Tag newTag = new Tag(tagName);
            tagRepository.save(newTag);
            return newTag;
        } else {
            return existingTag;
        }
    }

    @Override
    public ModuleDto findModuleById(Long moduleId) {
        Module module = findModuleOrThrow(moduleId);
        return modelMapper.map(module, ModuleDto.class);
    }

    @Transactional
    @Override
    public ModuleDto editModule(NewModuleDto newModuleDto, Long moduleId, String subscriptionType) {
        Module module = findModuleOrThrow(moduleId);

        module.setModuleName(newModuleDto.getModuleName().trim().toLowerCase().replaceAll("\\s+", "_"));
        module.setUserName(newModuleDto.getUserName().trim().toLowerCase().replaceAll("\\s+", "_"));
        changeStackName(newModuleDto, module);
        if ( module.getModuleName().isEmpty() || module.getUserName().isEmpty() || module.getStackName().isEmpty()) {
            throw new PayloadRequiredException();
        }
        if ( module instanceof TrialModule && "USER".equalsIgnoreCase(subscriptionType) ) {
            module = modelMapper.map(newModuleDto, RegisteredModule.class);
            ((RegisteredModule) module).setSubscriptionType("USER");
        } else if ( module instanceof RegisteredModule && "GUEST".equalsIgnoreCase(subscriptionType) ) {
            module = modelMapper.map(newModuleDto, TrialModule.class);
            ((TrialModule) module).setSubscriptionType("GUEST");
        } else {
            throw new InvalidSubscriptionTypeException("Invalid subscription type.");
        }
        module = moduleRepository.save(module);
        return modelMapper.map(module, ModuleDto.class);
    }

    public void changeStackName(NewModuleDto newModuleDto, Module module) {
        Stack newStack = stackRepository.findByStackNameIgnoreCase(newModuleDto.getStackName());
        if ( newStack == null ) {
            newStack = new Stack();
            newStack.setStackName(newModuleDto.getStackName().trim().toLowerCase().replaceAll("\\s+", "_"));
        }
        module.setStack(newStack);

        // Set the stackName of Module.
        module.setStackName(newStack.getStackName());
    }

    @Transactional
    @Override
    public boolean deleteModule(Long moduleId) {
        Module module = findModuleOrThrow(moduleId);
        cardRepository.findCardsByModuleId(moduleId).forEach(cardRepository::delete);
        moduleRepository.deleteById(moduleId);
        return !moduleRepository.existsById(moduleId);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<ModuleDto> findModulesByUserName(String userName) {

        if ( userName == null || userName.isEmpty() ) {
            throw new UserNotFoundException();
        }

        List<ModuleDto> modules = moduleRepository
                .findModulesByUserNameIgnoreCase(userName)
                .map(module -> modelMapper.map(module, ModuleDto.class))
                .collect(Collectors.toList());

        if ( modules.isEmpty() ) {
            throw new UserNotFoundException();
        }

        return modules;
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<ModuleDto> findModulesByPeriod(DatePeriodDto datePeriodDto) {
        return moduleRepository
                .findAllByDateCreatedBetween(datePeriodDto.getDateFrom(), datePeriodDto.getDateTo())
                .map(module -> modelMapper.map(module, ModuleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<ModuleDto> findModulesByName(String moduleName) {
        return moduleRepository
                .findModulesByModuleNameIgnoreCase(moduleName)
                .map(module -> modelMapper.map(module, ModuleDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PagedDataResponseDto<ModuleDto> findAllModules(PageRequest pageRequest) {
        Page<Module> modulePage = moduleRepository.findAll(pageRequest);
        PagedDataResponseDto<ModuleDto> pagedDataResponseDto = new PagedDataResponseDto<>();
        pagedDataResponseDto.setData(modulePage.getContent().stream()
                .map((Module module) -> modelMapper.map(module, ModuleDto.class))
                .collect(Collectors.toList()));
        pagedDataResponseDto.setTotalElements(modulePage.getTotalElements());
        pagedDataResponseDto.setTotalPages(modulePage.getTotalPages());
        return pagedDataResponseDto;
    }


    @Transactional(readOnly = true)
    @Override
    public Iterable<ModuleDto> findModulesByStack(String stackName) {
        return moduleRepository
                .findModulesByStackNameIgnoreCase(stackName)
                .map(module -> modelMapper.map(module, ModuleDto.class))
                .collect(Collectors.toList());
    }

    // FIXME
//    @Transactional(readOnly = true)
//    @Override
//    public Iterable<ModuleDto> finAllModulesByTags(Iterable<String> tags) {
//        return moduleRepository
//                .findAllByTags(new HashSet<>())
//                .map(module -> modelMapper.map(module, ModuleDto.class))
//                .collect(Collectors.toList());
//    }


    @Transactional(readOnly = true)
    @Override
    public int cardsAmountByModuleId(Long moduleId) {
        Module module = findModuleOrThrow(moduleId);
        return (module.getCards() != null) ? module.getCardsAmount() : 0;
    }
}
