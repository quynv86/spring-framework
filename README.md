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


##### Useful Commands:

SSH :  kubectl exec simple-api-6977df4968-sqsp5  --it -- /bin/sh

Expose Service: kubectl expose deploy simple-api --port 8181 --type=NodePort

Edit deployment: kubectl edit  deployment simple-api

SET IMAGES:  kubectl set image deployment simple-api simple-api=spring/sample-api

ROLLOUT STATUS: kubectl rollout status deployment/simple-api


#### About Volumes In Kubernetes:
Link: https://viblo.asia/p/kubernetes-series-bai-6-volume-gan-disk-storage-vao-container-OeVKB6rrKkW

#### Create Rancher
docker run -d --restart=unless-stopped -p 80:80 -p 443:443 --privileged  rancher/rancher:latest
 


