# Instructions of how to switch from jar to war package
1 - Make changes the main method by extending it as it follows:

```java
@SpringBootApplication
public class MobileAppWsApplication extends SpringBootServletInitializer {}
```
2 - Override the following method and put it at the top  as it follows:

```java
@SpringBootApplication
public class MobileAppWsApplication extends SpringBootServletInitializer {
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return	application.sources(MobileAppWsApplication.class);
    }
    
   //   rest of your codes can go her
}
```
3 - Add this to the pom.xml file 

```xml 
    <packaging>war</packaging>
```

- Place it between between version and name tags as displayed bellow

```xml 
    <version>0.0.1-SNAPSHOT</version>
    <packaging>war</packaging>
    <name>mobile-app-ws</name>
```

- Add this dependency at bottom in the dependencies sections

```xml 
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>
```

4 - Run bellow command to delete the target file 

```bash
    mvn clean
```
5 - To build the progect and generate the target file again run this: 

```bash
    mvn install
```
# Getting Started

### Reference Documentation
For further reference, please consider the following sections: