# FilmesFamosos

**Filmes Famosos** é o primeiro projeto do Nanodegree Android Developer da Udacity

- [Scopo do projeto](https://docs.google.com/document/d/e/2PACX-1vRTT2mabyTzJk5ENqKrhqhKDGysBL07XZphaL-hslYcXyMGh9_3WmCHvcq5AYBKIfHvei0FmvSCdp-j/pub)
- [Download Apk](https://github.com/ruyano/FilmesFamosos/blob/master/filmes_famosos_ruyano.apk)

<p align="center">
  <img src="https://i.imgur.com/Lpr67zi.gif" alt="Demo app"
       width="400" height="750">
</p>

## themoviedb API KEY
  - O projeto ustiliza a API do [themoviedb.org]( https://www.themoviedb.org/), por esse motivo é necessário a utilização de uma API KEY do mesmo, [Clique aqui](https://www.themoviedb.org/account/signup) para criar uma conta e pegar uma API_KEY
  
  - Sua API KEY deve ser adicionada no arquivo gradle.properties, em uma nova variável chamada: THEMOVIEDB_API_KEY

## O projeto utiliza AndroidX
  - Caso ocorra algum problema servifique-se de utilizar o Android Studio 3.2 RC 2 no mínimo e adicione as tags: 
  android.useAndroidX=true 
  android.enableJetifier=true  
  ao gradle.properties.
