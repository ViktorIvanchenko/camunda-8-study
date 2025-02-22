# Install cert-manager

```bash
kubectl create namespace cert-manager
helm upgrade -i cert-manager oci://registry-1.docker.io/bitnamicharts/cert-manager -n cert-manager --set installCRDs=true
```

# Install c8study

```bash
cd c8study
kubectl create namespace pivdenniy
helm dependency update
helm upgrade -i c8study . -n pivdenniy
```

## List issued CSR and certs provided by cert-manager

```bash
kubectl get certificaterequest -n pivdenniy
kubectl get certificate -n pivdenniy
```