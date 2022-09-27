<h1>SocialMeli 2</h1>

<h2>Introducción</h2>

<p>En esta entrega, se van a hacer las pruebas necesarias para confirmar el buen funcionamiento de SocialMeli. Vamos a realizar test unitarios, test con mocks y test de integración. Así vamos a conseguir verificar la eficiencia de nuestro código y asegurarnos de que todo funcione correctamente para la satisfacción de cada usuario.<p>

<h2>Dia 1</h2>
<p>Para este Sprint decidimos dividirnos en grupos de validaciones y grupos de tests.
En un principio trabajamos juntos para crear el esqueleto del paquete test. Para comenzar a trabajar, probamos la herramienta Code With Me de IntelliJ. Sin embargo, nos trajo muchos inconvenientes a la hora de hacer las pruebas de lo que íbamos implementando, por lo que decidimos finalmente trabajar con github.
Como en el Sprint 1, decidimos trabajar en un repositorio a parte (https://github.com/tomaslodola/be_java_hisp_w17_g01). Este es público para que nuestro scrum master pueda entrar cuando lo requiera y ver nuestros avances.
Creamos un excel en el que plasmamos las tareas a realizar, su estado y a quien está asignada para manejarnos con una mejor organización a nivel de grupo.
<strong>Notas:</strong> 
<strong>·</strong>La US-0002 no va a encontrarse implementada en el código. Esto es debido a que nosotros implementamos que el campo id_post sea autoenumerado, por lo que no fueron necesarias las validaciones
<strong>·</strong>Nos pusimos de acuerdo con nuestro scrum master y decidimos que todos los tests iban a implementarse desde la capa service. Por esto, todos los tests llevan mocks.
<strong>·</strong>Hicimos cambios en algunas implementaciones hechas en el Sprint 1 para poder implementar mejor algunos tests. Estos fueron en la clase UserController para el método getFollowedList y en la clase PublicationService para el método sortPublicationList.
</p>

<h2>Dia 2</h2>
<p>Para esta instancia, terminamos las tareas grupales y nos dedicamos a revisar inconvenientes de cada uno y a refactorizar juntos el código.
Viendo que contábamos con el tiempo suficiente, aprovechamos para hacer ciertos cambios y mejorar algunas implementaciones del código del Sprint 1. Tomamos en consideración el comentario que nos hicieron en la devolución del Sprint 1 y, también los comentarios que nos hicieron nuestros compañeros para el feedback de a pares.
Dejamos todo listo para que cada uno pueda comenzar con su parte individual.</p>

<h2>Equipo</h2>
<p>Facundo Gerez</p>
<p>Nicolas Prantl</p>
<p>Melisa Denis Scotto Sansó</p>
<p>Tomas Lodola</p>
<p>Ariel Agustin Lescano Gonzalez</p>
<p>Sabrina Micaela Cardoso</p>
<a href="https://docs.google.com/spreadsheets/d/1huk8_qeNs2PCPXXuhVtufaLxjpz_CiA-6_kaLwJmOq0/edit#gid=52424623">Endpoints y responsables.</a>


<h2>Tecnologías utilizadas</h2>
<p>Java 17</p>
<p>Spring</p>
<p>Git</p>
<p>Spring Test</p>
<p>Mockito</p>
<p>JUnit</p>
<p>MockMvc</p>

<h2>Herramientas</h2>
<p><strong>IntelliJ</strong> como IDE.</p>
<p></strong>Visual Studio Code</strong> para tener una mejor interfaz y hacer merges de forma más sencilla.</p>
<p></strong>GitHub</strong> como control de versionado del proyecto.</p>
<p></strong>Postman</strong> para realizar pruebas de lo implementado.</p>

<h2> Documentación técnica </h2>
<a href="https://docs.google.com/document/d/1Q-mctREte8rZXXhz2V1k5nRrGP5aizCa/edit">Requerimientos tecnicos funcionales.</a>
