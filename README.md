Práctica de Aplicaciones basadas en Componentes Distribuidos
===================

Objetivo
-------------
En esta segunda práctica de la asignatura se va a utilizar JAVA RMI para desarrollar una aplicación cliente/servidor de funcionalidad semejante a Twitter.

Planificación
-------------
Para el desarrollo de la práctica se ha seguido una metodología ágil que finalmente (tras las modificaciones durante el trascurso del proyecto) ha sido la siguiente:

#### Sprint 1 (27/11/2017-04/12/2017)

 - **Planificación** (Workitem)

#### Sprint 2 (04/12/2017-11/12/2017)

 - **Aplicacion cliente servidor simple** (feature):
-Implementación cliente (user-story)
-Implementación servidor(user-story)

#### Sprint 3 (11/12/2017-18/12/2017)

 - **Gestión de usuarios** (feature):
-Registro de usuarios(user-story)
-Login de usuarios(user-story)

#### Sprint 4 (24/12/2017-31/12/2017)

 - **Propio perfil** (feature):
-Mostrar datos propios(user-story)
-Establecer estados (user-story)
-Timeline propio (user-story)

#### Sprint 5 (01/01/2018-08/01/2018)

 - **Interacción usuarios** (feature):
-Búsqueda de Usuarios(user-story)
-Ver Usuario (user-story)
-Seguir Usuario(user-story)
-Actualizar Timeline  Dinamicamente (user-story)

#### Sprint 6 (08/01/2018-15/01/2018)

 - **Mensajes privados** (feature):
-Enviar mensaje(user-story)
-Bandeja de entrada (user-story)

 - **Memoria** (Workitem)


Arquitectura
-------------
![Diagrama de la arquitectura](/img/arch.png)

La arquitectura implementada en esta práctica consistirá en un modelo cliente-servidor. En él, la parte servidora mantendrá una serie de objetos   accesibles por los clientes utilizando interfaces remotas. Dichos clientes  invocarán  métodos de los  objetos utilizando RMI.  Para lograrla se han implementado los siguientes componentes:


#### Servidor

Dentro de la parte servidora encontramos las implementaciones de todos los objetos a los que será necesario acceder remotamente, así como las interfaces necesarias para permitir dicho acceso.

 - **Server:** implementación de la clase que materializa el servidor inicializando  la base de datos y el registro de objetos así como también el registro de callbacks.
 - **PlatformImpl:** implementación de la clase que materializa la aplicación y que realiza los accesos y modificaciones de la base de datos. Incluye todos los métodos necesarios para la interacción con esta última (buscar usuarios, añadir estados, seguir a un usuario...).
 - **UsuarioImpl:**  implementación correspondiente a los usuarios de la aplicación, incluyendo métodos correspondientes a las acciones que estos pueden realizar como editar su biografía o crear nuevos estados. No interaccionan directamente con la base de datos sino con la plataforma.
 - **EstadoImpl:** materialización de los estados que crean los usuarios en la aplicación. Como atributos incluyen el usuario que los ha publicado, el texto del estado y su fecha de publicación.
 - **MensajeImpl:** implementación de los mensajes privados que los usuarios se envían entre sí. Encapsula la información referente a remitente, destinatario, texto del mensaje y fecha de envío.
 - **RegistroCallbacks**:  clase encargada de mantener un registro de todos los callbacks que se deban disparar en algún momento determinado (cuando en la plataforma se añade o se borra algún estado). 

Además, dentro del package *interfaces* se incluyen las interfaces remotas que implementan las clases anteriormente mencionadas, necesarias para que los clientes puedan acceder a los métodos de forma remota. Además se incluye la interfaz ClienteCallback correspondiente en este caso a al objeto que materializará el callback y que esta vez se encontrará en la parte cliente.

#### Cliente

 - **Client:** clase encargada de materializar la conexión por parte de un cliente y de asociarla a un usuario después del inicio de sesión. Desencadena además toda la interfaz gráfica.
 - **ClienteCallbackImpl:** implementación del callback que permite  la notificación de nuevos estados cuando un usuario al que se sigue incluye uno nuevo. Tiene la peculiaridad que para este objeto es el servidor el que "accede remotamente". 
 
Dispone de las mismas *interfaces* que el servidor en un paquete del mismo nombre.

#### Interfaz gráfica

La interfaz gráfica está realizada enteramente utilizando Java Swing. Se han creado las siguientes ventanas:

 - **MenuInicial:** en él se muestran las opciones de login o registro.
 - **RegDialog:** solicita usuario y contraseña para registrar un nuevo usuario.
 - **LoginDialog:** solicita usuario y contraseña para iniciar sesión.
 - **MenúPrincipal:** ventana correspondiente al menú principal del usuario. Permite buscar a otros usuarios, ver el propio timeline así como ver el número de seguidores/seguidos/tweets.
 - **PerfilBuscado:** muestra la información de un perfil ajeno después de buscarlo en el menú principal.
 - **Perfil:** muestra de nuevo las estadísticas propias así como un timeline con únicamente los estados del usuario conectado. Permite añadir nuevos estados.
 - **Mensajes:** ventana en la que se pueden ver los mensajes privados recibidos y enviar nuevos mensajes a otros usuarios.
 - **NoExiste:** ventana de error indicando que el usuario buscado no existe.
 - **MensajeEnviado:** ventana de aviso indicando que el mensaje se ha enviado correctamente.

#### Base de datos

Para la implementación de la base de datos se ha utilizado el sistema gestor SQLite al no tratarse de una base de gran complejidad. Se materializa en un archivo *database.db* en la carpeta db dentro del directorio Server. Se han creado las siguientes tablas:

 - **USUARIOS:** en ella se guardan el usuario, la contraseña y la biografía de cada usuario.
 - **SEGUIMIENTOS**: se almacenan seguidor y seguido después de que un usuario pulse el botón de seguir.
 - **ESTADOS:**  en ella se almacenan el usuario que añade un estado, el texto del estado y su fecha de publicación.
 - **MENSAJES:** esta tabla guarda el remitente, destinatario, texto y fecha cada mensaje enviado.


Guía de uso
-------------

Se ha de ejecutar el proyecto Server y a continuación se ejecutarán los Clientes. En la primera ventana se nos preguntará si queremos registrarnos o logearnos si ya disponemos de un usuario. En ambos casos se nos pedirá el usuario y la contraseña para llevar a cabo la acción requerida.

![Login y registro](/img/1.jpg)

En caso de inicio de sesión correcto se mostrará el menú principal desde el cual se podrá buscar el perfil de otros usuarios, ver información como el número de seguidores/seguidos/tweets y el timeline con los tweets propios y los de los perfiles seguidos (se actualizarán automáticamente cuando un usuario publique un estado gracias a los callbacks).

![Menú principal](/img/2.png)

Si accedemos a la pestaña "Mi Perfil" se nos mostrarán de nuevo los datos del usuario conectado, su biografía editable, un timeline solo con sus tweets y se dará la posibilidad de publicar nuevos estados. También se nos muestra la opción "Ver Mensajes".

![Propio perfil](/img/3.png)

Al pulsar en la opción antes mencionada vemos una lista con los mensajes recibidos, así como un cuadro de texto en el que crear nuevos mensajes indicando el destinatario.

![Mensajes Privados](/img/4.png)

Al buscar un nombre de usuario desde el menú principal (en caso de que exista) se nos mostrarán sus datos, su biografía y todos los estados que ha publicado. También nos da la posibilidad de seguirlo/dejar de seguirlo.

![Perfil Buscado](/img/5.png)

Comentarios
-------------

Por algún motivo (o por un bug de java swing, según hemos leído en internet) a veces en algunos equipos,  al volver a la ventana de Menú Principal desde otras, los estados  del jList del timeline aparecen apilados o borrosos y se soluciona simplemente cambiando el tamaño de la ventana con el ratón.
