# GRPC
做一个GRPC的例子，初步体会一下。

Make a GRPC example and get a first-hand experience. 

此工程包含 [protobuf](https://developers.google.com/protocol-buffers/) 和 [GRPC](https://grpc.io/) 两部分。虽然之前有所了解过
相关内容，一是时间较长了，二是并没有做过GRPC相对完整的例子，三是我对此技术真的很想实际使用，所以今天来再次研究一下。

This project contains [protobuf](https://developers.google.com/protocol-buffers/) and [GRPC](https://grpc.io/). 
Although I learned relevant content previously, first is a long time before, second is that I have not done a relatively 
complete example of GRPC, and third is that I really want to actually use this technology, so I come to study again today.

### Circumstance
- Mac OS X
- Protobuf 3.5.1
- openssl 1.0.2o_1

### References
- [protobuf example](https://github.com/google/protobuf/tree/master/examples)
- [grpc example](https://github.com/grpc/grpc-java/tree/v1.12.0)

### Tips
工程采用gradle管理，请使用gradle任务进行编译，如果用idea进行编译，第一次静态语法检查无法通过。

This project use gradle management, please use gradle tasks to compile, if you use idea to compile, at first time, the 
static grammar check fails.

### About SSL Self-signed Certificate
使用以下命令声明自签名证书

```bash
# Changes these CN's to match your hosts in your environment if needed.
SERVER_CN=localhost
CLIENT_CN=localhost # Used when doing mutual TLS

echo Generate CA key:
openssl genrsa -passout pass:1111 -des3 -out ca.key 4096
echo Generate CA certificate:
# Generates ca.crt which is the trustCertCollectionFile
openssl req -passin pass:1111 -new -x509 -days 365 -key ca.key -out ca.crt -subj "/CN=${SERVER_CN}"
echo Generate server key:
openssl genrsa -passout pass:1111 -des3 -out server.key 4096
echo Generate server signing request:
openssl req -passin pass:1111 -new -key server.key -out server.csr -subj "/CN=${SERVER_CN}"
echo Self-signed server certificate:
# Generates server.crt which is the certChainFile for the server
openssl x509 -req -passin pass:1111 -days 365 -in server.csr -CA ca.crt -CAkey ca.key -set_serial 01 -out server.crt 
echo Remove passphrase from server key:
openssl rsa -passin pass:1111 -in server.key -out server.key
echo Generate client key
openssl genrsa -passout pass:1111 -des3 -out client.key 4096
echo Generate client signing request:
openssl req -passin pass:1111 -new -key client.key -out client.csr -subj "/CN=${CLIENT_CN}"
echo Self-signed client certificate:
# Generates client.crt which is the clientCertChainFile for the client (need for mutual TLS only)
openssl x509 -passin pass:1111 -req -days 365 -in client.csr -CA ca.crt -CAkey ca.key -set_serial 01 -out client.crt
echo Remove passphrase from client key:
openssl rsa -passin pass:1111 -in client.key -out client.key
echo Converting the private keys to X.509:
# Generates client.pem which is the clientPrivateKeyFile for the Client (needed for mutual TLS only)
openssl pkcs8 -topk8 -nocrypt -in client.key -out client.pem
# Generates server.pem which is the privateKeyFile for the Server
openssl pkcs8 -topk8 -nocrypt -in server.key -out server.pem
```

#### Hello world example with TLS (no mutual auth):

```bash
# Server
./build/install/examples/bin/hello-world-server-tls localhost 50440 ~/Downloads/sslcert/server.crt ~/Downloads/sslcert/server.pem
# Client
./build/install/examples/bin/hello-world-client-tls localhost 50440 ~/Downloads/sslcert/ca.crt
```

#### Hello world example with TLS with mutual auth:

```bash
# Server
./build/install/examples/bin/hello-world-server-tls localhost 54440 ~/Downloads/sslcert/server.crt ~/Downloads/sslcert/server.pem ~/Downloads/sslcert/client.crt
# Client
./build/install/examples/bin/hello-world-client-tls localhost 54440 ~/Downloads/sslcert/ca.crt ~/Downloads/sslcert/client.crt ~/Downloads/sslcert/client.pem
```