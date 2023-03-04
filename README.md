# This Project Is Full Demo For Spring Framework Application

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
```
docker run -d --restart=unless-stopped -p 80:80 -p 443:443 --privileged  rancher/rancher:latest
```
#### Update Deployment by command:
```
    kubectl set image deployment simple-api simple-api=localhost:5001/sample-api:v0.0.2
```

#### Create Docker Registry
```
docker run -d -p 5000:5000 --restart always --name registry registry:2
```


```aidl
kubectl delete pvc data-my-release-mariadb-0
kubectl delete pvc data-my-release-rabbitmq-0
helm install my-release bitnami/spring-cloud-dataflow

kubectl get pvc



# Install GIt Lab:
https://computingforgeeks.com/how-to-install-gitlab-ce-on-ubuntu-linux/
root Password: ednMQhtmjz3USvpIDhK781vKMp9KQDmNF9es/wKEVlQ=

Git Book: https://git-scm.com/book/en/v2/Git-Basics-Viewing-the-Commit-History
# Limit by times
git log --pretty=format:"%h %s" --graph --since=30.minutes

```

#### Port-forward to access the spring-cloud-data-flow
````aidl
kubectl port-forward service/scdf-spring-cloud-dataflow-server 9090:8080
````
#### Go to dashboard by flowing URL
````aidl
http://localhost:9090/dashboard
````