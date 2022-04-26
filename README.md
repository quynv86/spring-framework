# This Project Is Full Demo For Spring Application Framework

There are several modules as bellow: 
```aidl
    <modules>
      <module>spring-integration</module>
      <module>shared-domain</module>
      <module>spring-rest-api</module>
      <module>spring-config-server</module>
    </modules>
```

### 1.1 Shared - Domain
    This moudule is shared models are used in this project.

#### Source code for BOOK : 
https://github.com/apress/r2dbc-revealed
https://cloud.spring.io/spring-cloud-stream-binder-kafka/spring-cloud-stream-binder-kafka.html


#### RUN Kubenetes:
##### Create registry
docker run -d -p 5000:5000 --restart=always --name registry registry:2
docker tag srping/sample-api:lastest localhost:5000/sample-api


kubectl create service clusterip service-demo --tcp=8181:8181 --dry-run -o=yaml > deployment_service.yml

https://spring.io/guides/gs/spring-boot-kubernetes/


Ghi nho BUILD ca Project truoc khi Build tung Module