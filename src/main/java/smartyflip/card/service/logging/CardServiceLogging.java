/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.service.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

@Service
@Aspect
@Slf4j(topic = "Card service")
public class CardServiceLogging {

    @Pointcut("execution(* smartyflip.card.service.CardServiceImpl.findCardById(String)) && args(id)")
    public void findById(String id){}
}
