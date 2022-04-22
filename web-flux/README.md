### Tomcat ?
- Tomcat은 Spring MVC 에서 Servlet 에 대한 의존성이 존재했는데 Servlet 기술에 의존하지 않고 Web Flux 라는 기술을 쓸 수 있게끔함.
- Web Flux에서응 HTTP 를 지원하는 WebServer, Network Server 라면 어떤 형태의 라이브러리 던지 사용할 수 있다. (대표적인 것이 Netty임)

### RestController
- Web Flux 상에서 값을 리턴하는 경우에는 항상 Mono 혹은 Flux로 감싸 리턴해야 함.


## Mono
- 데이터가 0 .. 1 개인 데이터를 다룰 때 쓴다. 
### Just 
- 동기적으로 실행됨. 동기적으로 안의 로직이 수행되고 just 값으로 매핑이 되는 방식임. 
- 이미 Publishing 할 데이터가 준비가 되어 있다는 뜻임. 

### Publisher
- Mono, Flux 는 기본적으로 1개 이상의 Subscriber를 가질 수 있다.
### Hot Type, Cold Type
- 어떤 Subscriber 가 호출을 하든 고정되 값을 반환하는 것을 Cold Type Source 라고 한다. (replay 라고도 함) 

### Block
- block 메서드는 내부에서 subscribe 를 한번 수행한다. 
- 실제로 blocking 으로 동작하기 때문에 Publisher 가 제공하는 결과값을 Flux, Mono 컨테이너에게 값을 넘겨주기 위해서 사용한다.
- 다만 그렇기 때문에 코드에서는 되도록 사용하지 않는게 좋음. 

- 중요한 점은 여러 연산들을 조합하여 사용할 수 있지만 실행이 되는 것은 아니라는 것. 실행은 어딘가에 있는 Subscriber 에 의해서 실행된다는 것이 중요하다. 

## Flux
- 데이터가 여러개인 것 (Collection 0 .. N) 을 다룰 때 쓴다. 
### 
