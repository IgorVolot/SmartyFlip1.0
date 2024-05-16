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
import smartyflip.modules.dao.ModuleRepository;
import smartyflip.modules.dao.TagRepository;
import smartyflip.modules.dto.DatePeriodDto;
import smartyflip.modules.dto.ModuleDto;
import smartyflip.modules.dto.NewModuleDto;
import smartyflip.modules.model.Module;
import smartyflip.modules.service.exceptions.ModuleNotFoundException;
import smartyflip.modules.service.exceptions.UnauthorizedAccessException;
import smartyflip.modules.service.util.RegisteredModule;
import smartyflip.modules.service.util.TrialModule;
import smartyflip.stacks.dao.StackRepository;
import smartyflip.stacks.model.Stack;
import smartyflip.stacks.service.exceptions.StackNotFoundException;
import smartyflip.utils.PagedDataResponseDto;

import java.util.Optional;
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


    private Module findModuleOrThrow(Long moduleId) {
        return moduleRepository.findById(moduleId).orElseThrow(ModuleNotFoundException::new);
    }

    @Transactional
    @Override
    public ModuleDto addModule(NewModuleDto newModuleDto, String subscriptionType) {
        // Retrieve UserAccount
        Optional<UserAccount> userAccountOpt = userRepository.findByUsername(newModuleDto.getUsername());
        if ( userAccountOpt.isEmpty() ) {
            throw new UserNotFoundException();
        }
        UserAccount userAccount = userAccountOpt.get();

        // Check if the user is an administrator
        boolean isAdmin = userAccount.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ADMINISTRATOR"));

        // Check access permissions based on subscription type
        if ( "GUEST".equalsIgnoreCase(subscriptionType) && !isAdmin ) {
            throw new InvalidSubscriptionTypeException("Creating a GUEST module is restricted to ADMIN users only.");
        }

        // Determine the type of module based on subscription type and map fields
        Module module;
        if ( "GUEST".equalsIgnoreCase(subscriptionType) ) {
            module = modelMapper.map(newModuleDto, TrialModule.class);
            ((TrialModule) module).setSubscriptionType("GUEST");
        } else if ( "USER".equalsIgnoreCase(subscriptionType) ) {
            module = modelMapper.map(newModuleDto, RegisteredModule.class);
            ((RegisteredModule) module).setSubscriptionType("USER");
        } else {
            throw new InvalidSubscriptionTypeException("Invalid subscription type.");
        }

        // Set common fields
        module.setUserAccount(userAccount);
        module.setUserName(userAccount.getUsername()); // This ensures user_name is set


        // Check if the Stack already exists
        Stack stack = stackRepository.findByStackNameIgnoreCase(newModuleDto.getStackName());
        if (stack == null) {
            throw new StackNotFoundException();
        }

        // Set the stack and save
        module.setStack(stack);
        module = moduleRepository.save(module);
        return modelMapper.map(module, ModuleDto.class);
    }

    @Transactional
    public ModuleDto editModule(NewModuleDto newModuleDto, Long moduleId) {
        // Retrieve the existing Module
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(ModuleNotFoundException::new);

        // Retrieve UserAccount using username from DTO
        Optional<UserAccount> userAccountOpt = userRepository.findByUsername(newModuleDto.getUsername());
        if (userAccountOpt.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserAccount userAccount = userAccountOpt.get();

        // Check if the user is an administrator
        boolean isAdmin = userAccount.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ADMINISTRATOR"));

        // Check permissions for registered users
        if (!isAdmin && !module.getUserAccount().getUsername().equals(newModuleDto.getUsername())) {
            throw new UnauthorizedAccessException("User is not authorized to edit this module.");
        }

        // Validate and set stackName if provided
        if (newModuleDto.getStackName() != null) {
            Stack stack = stackRepository.findByStackNameIgnoreCase(newModuleDto.getStackName());
            if (stack == null) {
                throw new StackNotFoundException();
            }
            module.setStack(stack);
        }

        // Set moduleName if provided
        if (newModuleDto.getModuleName() != null) {
            module.setModuleName(newModuleDto.getModuleName());
        }

        // Save changes
        module = moduleRepository.save(module);
        return modelMapper.map(module, ModuleDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<ModuleDto> findModulesByUserId(Integer userId) {
        UserAccount userAccount = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return moduleRepository
                .findModulesByUserAccount(userAccount)
                .map(module -> modelMapper.map(module, ModuleDto.class))
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    @Override
    public ModuleDto findModuleById(Long moduleId) {
        Module module = findModuleOrThrow(moduleId);
        return modelMapper.map(module, ModuleDto.class);
    }
    @Transactional
    @Override
    public ModuleDto deleteModule(Long moduleId) {
        Module module = findModuleOrThrow(moduleId);
        cardRepository.findCardsByModuleId(moduleId).forEach(cardRepository::delete);
        moduleRepository.deleteById(moduleId);
        return modelMapper.map(module, ModuleDto.class);
    }


    @Transactional(readOnly = true)
    @Override
    public Iterable<ModuleDto> findModulesByPeriod(DatePeriodDto datePeriodDto) {
        return moduleRepository
                .findAllByDateCreatedBetween(datePeriodDto.getDateFrom().atStartOfDay(), datePeriodDto.getDateTo().atStartOfDay())
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

    @Transactional(readOnly = true)
    @Override
    public int cardsAmountByModuleId(Long moduleId) {
        Module module = findModuleOrThrow(moduleId);
        return (module.getCards() != null) ? module.getCardsAmount() : 0;
    }
}
