package moviebuddy;

import com.github.benmanes.caffeine.cache.Caffeine;
import moviebuddy.cache.CachingAspect;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource("/application.properties")
@ComponentScan(basePackages = { "moviebuddy" })
@Import({ MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class })
@EnableAspectJAutoProxy
public class MovieBuddyFactory {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("moviebuddy");

        return marshaller;
    }

    @Bean
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS));

        return cacheManager;
    }

    @Bean
    public CachingAspect cachingAspect(CacheManager cacheManager) {
        return new CachingAspect(cacheManager);
    }

    /*@Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public Advisor cachingAdvisor(CacheManager cacheManager) {
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, CacheResult.class);
        Advice advice = new CachingAdvice(cacheManager);

//        Advisor = PointCut(대상 선정 알고리즘) + Advice(부가기능)
        return new DefaultPointcutAdvisor(pointcut, advice);
    }*/

    @Configuration
    static class DomainModuleConfig {

    }

    @Configuration
    static class DataSourceModuleConfig {

    }
}
