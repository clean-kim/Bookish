package kr.clean.bookish.configuration

import kr.clean.bookish.annotation.LynnDB
import org.apache.ibatis.session.*
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.util.*
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    @Bean("lynnDatasource")
    @Primary
    @ConfigurationProperties(prefix = "datasource.db-lynn")
    fun lynnDatasource(): DataSource {
        return DataSourceBuilder.create().build()
    }
}

@Configuration
@MapperScan(basePackages = ["kr.clean.bookish"], annotationClass = LynnDB::class, sqlSessionFactoryRef = "lynnSqlSessionFactory")
class MybatisApplicationConfig {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Bean("lynnSqlSessionFactory")
    @Throws(Exception::class)
    fun lynnSqlSessionFactory(@Qualifier("lynnDatasource") dataSource: DataSource?): SqlSessionFactory? {
        val sessionFactoryBean = SqlSessionFactoryBean()
        val pathResolver = PathMatchingResourcePatternResolver()
        sessionFactoryBean.setDataSource(dataSource)
        sessionFactoryBean.setTypeAliasesPackage("kr.clean.bookish.model")
        sessionFactoryBean.setConfigLocation(pathResolver.getResource("classpath:mybatis/MybatisConfiguration.xml"))
        sessionFactoryBean.setMapperLocations(*pathResolver.getResources("classpath*:mapper/*Mapper.xml"))
        val config = Objects.requireNonNull(sessionFactoryBean.getObject())!!.configuration
        if (config != null) {
            config.isUseGeneratedKeys = true
            config.defaultExecutorType = ExecutorType.REUSE
            config.autoMappingBehavior = AutoMappingBehavior.PARTIAL
            config.isMapUnderscoreToCamelCase = false
        }
        sessionFactoryBean.vfs = SpringBootVFS::class.java
        return sessionFactoryBean.getObject()
    }
}