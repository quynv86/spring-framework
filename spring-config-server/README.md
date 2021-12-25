### Config server essentials
#### 1. Reference links:
    https://cloud.spring.io/spring-cloud-config/multi/multi__spring_cloud_config_server.html
    1.1 Support JDBC Backend Config Server.

#### 1.1 Config Git As Configuration Repository
```aidl
#  cloud:
#    config:
#      server:
#        git:
#          uri: file:///Users/quynv/Research/config-local
```

#### 1.2 Config JDBC As Configuration Repository - This option is quite useful when we build a configuration application to share for all applications in our company
     +) Adding spring-jdbc into project dependency and create table PROPERTIES as bellow:
    create table PROPERTIES (
        id identity,
        application varchar(100),
        profile varchar(100),
        label varchar(100),
        key varchar(100),
        value varchar(100)
    );
    + Insert some properties 
    
    insert into PROPERTIES(application, profile, label, key, value)
    values('spring-cloud-integration','','','logging.level.root','DEBUG');

#### 1.3 Next section



