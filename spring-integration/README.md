
#I. Cấu chung cho project
## 1. Cấu hình file pom
##### 1.1. Trong trường hợp project spirng-integraion là con của project spring-framework. Trong file POM.xml phải chỉ rõ thuộc tính relativePath trong đường thẻ PARENT. Ví dụ: 
```aidl
    <parent>
        <groupId>vn.quynv.springframework</groupId>
        <artifactId>spring-framework</artifactId>
        <version>1.0-SNAPSHOT</version>
        <relativePath>../</relativePath>
    </parent>
```

##### 1.2. Cấu hình các spring-integration dependecies:
```aidl
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-integration</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.integration</groupId>
            <artifactId>spring-integration-file</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.integration</groupId>
            <artifactId>spring-integration-http</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.integration</groupId>
            <artifactId>spring-integration-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```


