# Kubernetes Tutorial

# Create Cluster on Google Cloud
cloud.google.com -> create account -> kubernetes engine -> create cluster

# Open Cloud Shell on Google Cloud
cloud shell icon -> run the following commands

# Kubernetes Controller
kubeclt

# Deploying Docker image to Cluster
kubectl create deployment imageName --image=dockerHubUsername/imageName:versionOrTag

# Exposing the deployment
kubectl expose deployment deploymentName --type=LoadBalancer --port=8080

# Accessing application in cluster 
http://34.69.25.106:8080/hello-world-bean

# Events
kubectl get events

# Pods
kubectls get pod/s

# Replicasets
kubectl get replicaset/s

# Services
kubectl get service/s

# Create Deployment
kubectl create deployment... -> deployment, replicaset & pod

# Expose Deployment
kubectl expose deployment... -> service

# Pods & Containers
-> a container lives inside a pod

# Pods details
kubectl get pods -o wide
-> each pod has a unique IP adress
-> see the number of containers inside a specific pod

# Pod documentation
kubectl explain pods
-> node -> each has multiple pods -> each has multiple containers

# Pod Describe
kubectl describe pod pod-name-id
-> lot of details of that specific pod

# Replicaset Details
kubectl get replicaset
-> see how many pods are running at all time
-> see how many containers are in ready status

# Deleting a specific pod
kubectl get pods -o wide
kubectl delete pods pod-name-id

# Verifying deleted pod
kubectl get pods -o wide
-> pode end id will be different now
-> even deleting pod the url keeps running
-> replicaset keeps monitoring
kubectl get replicaset
-> DESIRED = NUMBER-NEEDED
-> if the number of pods is less than desired number
-> replicaset launches it automatically

# Scaling desired number of pods
kubectl scale deployment hello-world-rest-api --replicas=3
-> scale to the number of pods setted above

# Verifying number of pods
kubectl get pods
-> number of pods incresed to 34

# Seeing it in action
go to url -> http://34.69.25.106:8080/hello-world 
-> every time you request a get request method
-> kuberntes takes care of load balacing
-> it will eventually change the application

# Verifying Replicaset
kubectl get replicaset
-> DESIRED number changed

# Verifying Events
kubectl get events
-> Verifying what happened in the background

# Verifying Events in a sorted order
kubectl get events --sort-by=.metadata.creationTimestamp

# Replicaset Documentation
kubectl explain replicaset

# Updating a deployed application with zero downtime
kubectl get replicaset
-> number of pods running
kubectl get replicaset -o wide
-> get details of the docker images related to the replicaset
kubectl set image deployment  hello-world-rest-api hello-world-rest-api=DUMMY_IMAGE:TEST
-> that's a wrong image, but the old version is still running
kubectl get replicaset -o wide
-> see what happened in the background
-> we can see that we have two instances of the replicaset
-> we can see that we have any in READY status for the second instance
-> that happends because of the image error
kubectl get pods
-> we will see that the pod related to the wrong image, won't have it's container running
kubectl describe pod pod-image-id (with wrong image)
-> you can see the details that generated that error
get events sorted by time
-> see detais in the background
-> RUNNING IT IN THE CORRECT WAY
kubectl set image deployment  hello-world-rest-api hello-world-rest-api=in28min/hello-world-rest-api:0.0.2.RELEASE
-> kubectl set image deployment image_name image_name=docker_hub_username/image_name:version
-> image_name = pod_name_without_id
-> version = tag

# Verifying the pods being replaced
kubectl get pods
-> you will see the older version pods being terminated

# Verifying the replicasets
kubectl get replicaset
-> you will see the first replicaset with 0 ready pods
-> you will see the wrong image replicaset with 0 ready pods
-> you will see the wrong third replicaset with the 3 ready pods that replaced the first ones

# Verifying requests
-> you will see the version 2 features on production server

# What happened in the background
kubectl get events --sort-by=.metadata.creationTimestamp
peluiznogueiram@cloudshell:~$ kubectl get events --sort-by=.metadata.creationTimestamp                  LAST SEEN   TYPE      REASON                   OBJECT                                       MESSAGE
41m         Normal    Killing                  pod/hello-world-rest-api-687d9c7bc7-pb896    Stopping container hello-world-rest-api                                                                             41m         Normal    SuccessfulCreate         replicaset/hello-world-rest-api-687d9c7bc7   Created pod: hello-world-rest-api-687d9c7bc7-659z8
41m         Normal    Scheduled                pod/hello-world-rest-api-687d9c7bc7-659z8    Successfully assigned default/hello-world-rest-api-687d9c7bc7-659z8 to gke-pedroluiznogueira-cl-default-pool-23c9209c-wzk1                                                                                                  41m         Warning   FailedMount              pod/hello-world-rest-api-687d9c7bc7-659z8    MountVolume.SetUpfailed for volume "kube-api-access-sn7wc" : failed to sync configmap cache: timed out waiting for the condition                                                                                           41m   Normal    Pulling                  pod/hello-world-rest-api-687d9c7bc7-659z8    Pulling image "in28min/hello-world-rest-api:0.0.1.RELEASE"                                                          41mNormal    Pulled                   pod/hello-world-rest-api-687d9c7bc7-659z8    Successfully pulled image "in28min/hello-world-rest-api:0.0.1.RELEASE" in 4.047454083s                              41m         Normal    Started                  pod/hello-world-rest-api-687d9c7bc7-659z8    Started container hello-world-rest-api                                                                              41m         Normal    Created                  pod/hello-world-rest-api-687d9c7bc7-659z8    Created container hello-world-rest-api
36m         Normal    SuccessfulCreate         replicaset/hello-world-rest-api-687d9c7bc7   Created pod: hello-world-rest-api-687d9c7bc7-kf8rf
36m         Normal    ScalingReplicaSet        deployment/hello-world-rest-api              Scaled up replica set hello-world-rest-api-687d9c7bc7 to 3
36m         Normal    Scheduled                pod/hello-world-rest-api-687d9c7bc7-jcqkc    Successfully assigned default/hello-world-rest-api-687d9c7bc7-jcqkc to gke-pedroluiznogueira-cl-default-pool-23c9209c-rfpj                                                                                                  36m         Normal    SuccessfulCreate         replicaset/hello-world-rest-api-687d9c7bc7   Created pod: hello-world-rest-api-687d9c7bc7-jcqkc                                                                  36m         Normal    Scheduled                pod/hello-world-rest-api-687d9c7bc7-kf8rf    Successfully assigned default/hello-world-rest-api-687d9c7bc7-kf8rf to gke-pedroluiznogueira-cl-default-pool-23c9209c-rfpj
36m         Normal    Started                  pod/hello-world-rest-api-687d9c7bc7-jcqkc    Started container hello-world-rest-api
36m         Normal    Pulled                   pod/hello-world-rest-api-687d9c7bc7-kf8rf    Container image "in28min/hello-world-rest-api:0.0.1.RELEASE" already present on machine
36m         Normal    Started                  pod/hello-world-rest-api-687d9c7bc7-kf8rf    Started container hello-world-rest-api
36m         Normal    Pulled                   pod/hello-world-rest-api-687d9c7bc7-jcqkc    Container image "in28min/hello-world-rest-api:0.0.1.RELEASE" already present on machine
36m         Normal    Created                  pod/hello-world-rest-api-687d9c7bc7-jcqkc    Created container hello-world-rest-api
36m         Normal    Created                  pod/hello-world-rest-api-687d9c7bc7-kf8rf    Created container hello-world-rest-api
21m         Normal    Scheduled                pod/hello-world-rest-api-84d8799896-rlr5n    Successfully assigned default/hello-world-rest-api-84d8799896-rlr5n to gke-pedroluiznogueira-cl-default-pool-23c9209c-wzk1
21m         Normal    ScalingReplicaSet        deployment/hello-world-rest-api              Scaled up replica set hello-world-rest-api-84d8799896 to 1
21m         Normal    SuccessfulCreate         replicaset/hello-world-rest-api-84d8799896   Created pod: hello-world-rest-api-84d8799896-rlr5n
11m         Warning   InspectFailed            pod/hello-world-rest-api-84d8799896-rlr5n    Failed to apply default image tag "DUMMY_IMAGE:TEST": couldn't parse image reference "DUMMY_IMAGE:TEST": invalid reference format: repository name must be lowercase
19m         Warning   Failed                   pod/hello-world-rest-api-84d8799896-rlr5n    Error: InvalidImageName
11m         Warning   FailedToUpdateEndpoint   endpoints/hello-world-rest-api               Failed to update endpoint default/hello-world-rest-api: Operation cannot be fulfilled on endpoints "hello-world-rest-api": the object has been modified; please apply your changes to the latest version and try again
11m         Normal    SuccessfulDelete         replicaset/hello-world-rest-api-84d8799896   Deleted pod: hello-world-rest-api-84d8799896-rlr5n
11m         Normal    ScalingReplicaSet        deployment/hello-world-rest-api              Scaled up replica set hello-world-rest-api-7ddff5dfc6 to 1
11m         Normal    SuccessfulCreate         replicaset/hello-world-rest-api-7ddff5dfc6   Created pod: hello-world-rest-api-7ddff5dfc6-p7bz9
10m         Normal    Scheduled                pod/hello-world-rest-api-7ddff5dfc6-p7bz9    Successfully assigned default/hello-world-rest-api-7ddff5dfc6-p7bz9 to gke-pedroluiznogueira-cl-default-pool-23c9209c-rfpj
11m         Normal    ScalingReplicaSet        deployment/hello-world-rest-api              Scaled downreplica set hello-world-rest-api-84d8799896 to 0
10m         Normal    Pulling                  pod/hello-world-rest-api-7ddff5dfc6-p7bz9    Pulling image "in28min/hello-world-rest-api:0.0.2.RELEASE"
10m         Normal    Pulled                   pod/hello-world-rest-api-7ddff5dfc6-p7bz9    Successfully pulled image "in28min/hello-world-rest-api:0.0.2.RELEASE" in 1.822922336s
10m         Normal    SuccessfulDelete         replicaset/hello-world-rest-api-687d9c7bc7   Deleted pod: hello-world-rest-api-687d9c7bc7-kf8rf
10m         Normal    ScalingReplicaSet        deployment/hello-world-rest-api              Scaled up replica set hello-world-rest-api-7ddff5dfc6 to 2
10m         Normal    ScalingReplicaSet        deployment/hello-world-rest-api              Scaled downreplica set hello-world-rest-api-687d9c7bc7 to 2
10m         Normal    Killing                  pod/hello-world-rest-api-687d9c7bc7-kf8rf    Stopping container hello-world-rest-api
10m         Normal    Created                  pod/hello-world-rest-api-7ddff5dfc6-p7bz9    Created container hello-world-rest-api
10m         Normal    Started                  pod/hello-world-rest-api-7ddff5dfc6-p7bz9    Started container hello-world-rest-api
10m         Normal    Scheduled                pod/hello-world-rest-api-7ddff5dfc6-bm7mt    Successfully assigned default/hello-world-rest-api-7ddff5dfc6-bm7mt to gke-pedroluiznogueira-cl-default-pool-23c9209c-wzk1
10m         Normal    SuccessfulCreate         replicaset/hello-world-rest-api-7ddff5dfc6   Created pod: hello-world-rest-api-7ddff5dfc6-bm7mt
10m         Normal    Pulling                  pod/hello-world-rest-api-7ddff5dfc6-bm7mt    Pulling image "in28min/hello-world-rest-api:0.0.2.RELEASE"
10m         Normal    Started                  pod/hello-world-rest-api-7ddff5dfc6-bm7mt    Started container hello-world-rest-api
10m         Normal    Pulled                   pod/hello-world-rest-api-7ddff5dfc6-bm7mt    Successfully pulled image "in28min/hello-world-rest-api:0.0.2.RELEASE" in 1.790776363s
10m         Normal    Created                  pod/hello-world-rest-api-7ddff5dfc6-bm7mt    Created container hello-world-rest-api
10m         Normal    SuccessfulCreate         replicaset/hello-world-rest-api-7ddff5dfc6   Created pod: hello-world-rest-api-7ddff5dfc6-c5r59
10m         Normal    ScalingReplicaSet        deployment/hello-world-rest-api              Scaled up replica set hello-world-rest-api-7ddff5dfc6 to 3
10m         Normal    SuccessfulDelete         replicaset/hello-world-rest-api-687d9c7bc7   Deleted pod: hello-world-rest-api-687d9c7bc7-jcqkc
10m         Normal    ScalingReplicaSet        deployment/hello-world-rest-api              Scaled downreplica set hello-world-rest-api-687d9c7bc7 to 1
10m         Normal    Scheduled                pod/hello-world-rest-api-7ddff5dfc6-c5r59    Successfully assigned default/hello-world-rest-api-7ddff5dfc6-c5r59 to gke-pedroluiznogueira-cl-default-pool-23c9209c-rfpj
10m         Normal    Killing                  pod/hello-world-rest-api-687d9c7bc7-jcqkc    Stopping container hello-world-rest-api
10m         Normal    ScalingReplicaSet        deployment/hello-world-rest-api              Scaled downreplica set hello-world-rest-api-687d9c7bc7 to 0
10m         Normal    Started                  pod/hello-world-rest-api-7ddff5dfc6-c5r59    Started container hello-world-rest-api
10m         Normal    Created                  pod/hello-world-rest-api-7ddff5dfc6-c5r59    Created container hello-world-rest-api
10m         Normal    Killing                  pod/hello-world-rest-api-687d9c7bc7-659z8    Stopping container hello-world-rest-api
10m         Normal    SuccessfulDelete         replicaset/hello-world-rest-api-687d9c7bc7   Deleted pod: hello-world-rest-api-687d9c7bc7-659z8
10m         Normal    Pulled                   pod/hello-world-rest-api-7ddff5dfc6-c5r59    Container image "in28min/hello-world-rest-api:0.0.2.RELEASE" already present on machine

# Architecture overview
-> a application has a DEPLOYMENT -> each DEPLOYMENT can have multiple REPLICASETS -> each REPLICASET can have multiple PODS -> each POD can have multiple CONTAINERS -> each CONTAINER is a PROJECT APPLICATION (in any programming language)

# Updating production application background overview
-> new REPLICASET is created with one instance -> one POD is created -> there's no need for 3 instances of the older version APPLICATION -> the version 1 REPLICASET is scaled down to 2 PODS -> the version 2 REPLICASET is scaled up to 2 PODS -> for every POD created a CONTAINER is created aswell -> the version 1 REPLICASET is scaled down to 1 -> the version 2 REPLICASET is scaled up to 3

# Understanding Services
kubectl delete pod hello-world-rest-api-7ddff5dfc6-p7bz9
-> new pod is created
hello-world-rest-api-7ddff5dfc6-lhsh9 with another IP ADRESS
-> that happens because of services
-> pods are trowable units
-> we don't want the consumer to use different urls
-> service allow your application to receive traffic through a permanent IP ADRESS
-> a loud balancer is created in the google cloud for this specific IP ADRESS
kubectl get services
-> all services
-> see types (LoadBalancer...)
-> Loadbalancer can load balance between between multiple ports
-> Cluster IP service can only be accessed from inside the cluster 
