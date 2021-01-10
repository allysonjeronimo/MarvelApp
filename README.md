# MarvelApp

![prints](https://user-images.githubusercontent.com/32485354/104138317-1948fe00-5382-11eb-9659-c0daee1b7f08.png)

## Sobre o projeto

App Android nativo em desenvolvimento que simula uma loja online de quadrinhos da Marvel.

## Funcionalidades

- Consulta de Quadrinhos (Randomicamente alguns quadrinhos são definidos como raros)
- Detalhes de Quadrinho
- Carrinho de Compras
- Cupons de desconto (Que podem ser Raros e Comuns)
- Checkout

## Instruções

### Executar projeto

- Realizar cadastro na página de desenvolvedores da Marvel e obter as credenciais de aceso da [API](https://developer.marvel.com/).
- Modificar arquivo [Constants.kt](https://github.com/allysonjeronimo/MarvelApp/blob/master/app/src/main/java/com/allysonjeronimo/marvelapp/data/network/Constants.kt) do projeto com os dados fornecidos (PuBLIC_KEY e PRIVATE_KEY)

const val BASE_URL = "https://gateway.marvel.com/v1/public/"
const val PUBLIC_KEY = ""
const val PRIVATE_KEY = ""

### Aplicar descontos

- Há dois tipos de Cupons de Descontos (Comum e Raro). Cupons comuns permitem desconto de 10% em
quadrinhos comuns, enquanto que Cupons Raros adicionam desconto de 25% em todos os quadrinhos.

- Após adicionar itens no carrinho, utilizar algum dos seguintes códigos: 

123456 (Cupom Comum)
654321 (Cupom Raro)

## Tecnologias:

- Kotlin
- MVVM
- Room
- Retrofit
- Architecture Components
- Coroutines
- Navigation Component
- Picasso






