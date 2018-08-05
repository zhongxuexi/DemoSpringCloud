//package com.jess.config.dao;
//
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//
///**
// * Created by zhongxuexi on 2018/6/3.
// */
//@Configuration
//public class SessionFactoryConfig {
//    @Value("${mybatis-config-file}")
//    private String mybatisConfigFilePath;
//    @Value("${mapper-path}")
//    private String mapperPath;
//    @Value("${entity-package}")
//    private String entityPackage;
//    @Autowired
//    @Qualifier("dataSource")
//    private DataSource dataSource;
//
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFilePath));
//
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        String packagesSearchPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packagesSearchPath));
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setTypeAliasesPackage(entityPackage);
//        return sqlSessionFactoryBean;
//    }
//}
