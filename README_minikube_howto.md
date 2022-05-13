# Vis-lab mit einem Minikube-Cluster ausführen

In den folgenden Abschnitten wird die Vorgehensweise beleuchtet, wie die in Docker containerisierten Microservice nun in einem Kubernetes-Cluster (Minikube) deployed werden können. Ebenso wird der Cluster um eine Istio Installation ergänzt, um ein Service Mesh zu realisieren.

## Voraussetzungen

- `minikube` und `kubectl` müssen installiert sein.

- `istioctl` und die zugehörigen Addons müssen installiert sein, z.B. wie [hier](https://github.com/hka-iwi-vislab/microservice-istio/blob/master/WIE-LAUFEN.md) erklärt.

## Vorgehensweise für Erstellen des Clusters

- Starte den Minikube-Cluster:

```
minikube start
```

- Konfiguration des Clusters einbetten

```
kubectl apply -f deployment.yml
```

An dieser Stelle werden die Deployments erstellt und durch die Services nach außen exponiert. Die aktuellen Docker Images werden dabei aus DockerHub heruntergeladen und dann innerhalb eines Pods ausgeführt.
Um zu überprüfen, ob die Pods laufen, lässt sich folgender Befehl eingeben:

```
kubectl get pods
```

und analog können wir auch die Services ausgeben lassen, um auch zu betrachten, auf welchen Ports die laufen:

```
kubectl get services
```

Damit wir nun Anfragen an die einzelnen Microservices abschicken können, müssen wir noch herausfinden, an welche Adresse die Anfrage geschickt werden muss, damit die Anfrage das Cluster erreicht und innerhalb des Clusters delegiert werden kann. Dies erfolgt durch:

```
minikube ip
```

An diese Adresse, hinzugefügt durch die entsprechenden Zusätze "/product/" und "/category/", können nun die Anfragen gesendet werden, zum Beispiel:
`http://[ip-address]:[port]/product/products` um alle Produkte zu erhalten. Hierbei erhält man den Port durch die Auflistung der Services. Dabei nimmt man den Port vom Apache-Service.

## Vorgehensweise, um Istio einzubetten

Zunächst wird die Istio-Konfiguration eingebettet, ähnlich wie die vorherige Konfiguration übernommen wurde:

```
kubectl apply -f istioconf.yml
```

Ebenso werden die Addons benötigt. Diese erhält man durch das Herunterladen von Istio (diese sind nicht im Repository inkludiert!)

```
kubectl apply -f samples/addons
```

Danach wird der Apache-Pod nicht mehr benutzt, da der IngressGateway von Istio die Funktion des LoadBalancers übernimmt, daher kann das Apache-Deployment entfernt werden:

```
kubectl delete deployments.apps apache
```

Als nächstes können dann wieder Anfragen an die Services geschickt werden: hier erfährt man den Port wieder durch die Services, diesmal durch den IngressGateway. Da dieser in einem anderen Namespace ist, muss der Befehl etwas abgeändert werden:

```
kubectl get services -n istio-system
```

Ansonsten ändert sich an dem Request nichts.

Um nun die eingebauten Komponenten (Kiali, Prometheus, Grafana) vom eigenen Host zu erreichen, müssen diese Ports weitergeleitet werden. Die Ports findet man auch für diese Services durch den oben genannten Befehl heraus. Dann muss folgender Befehl eingesetzt werden, um lokal auf z.B. Prometheus zuzugreifen. Prometheus läuft üblicherweise auf dem Port 9090, und dieser wird weitergeleitet auf den Port 9090 im lokalen Host.

```
kubectl port-forward -n istio-system [prometheus-pod-name] 9090:9090
```

Anschließend lässt sich dann durch `localhost:9090` die Oberfläche von Prometheus betrachten. Für die anderen Services (Kiali, Grafana) erfolgt der Schritt analog.