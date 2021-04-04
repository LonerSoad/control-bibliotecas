# control-bibliotecas

En este repo he colocado el proyecto  y documentación solicitado en la entrevista del INE 

# Proceso para ejecutar los containers:
  
  ## Crear Directorios en PC host
    * mkdir /archivos
     
  ## Crear Red Para la Comunicación entre los Containers
    * docker network create redControlBiblioteca --subnet 192.0.0.0/24 --gateway 192.0.0.200
  
  ## Construir Containers
    *docker-compose -f docker.yml build mariadb
    *docker-compose -f docker.yml build control-bibliotecas
  
  ## Ejecutar Containers
    * docker-compose -f docker.yml up -d  mariadb
    * docker-compose -f docker.yml up control-bibliotecas
    
  ### Notas:
    * Decidí no crear un Jenkinsfile, debido a que al ser 2 servicios,  estaría muy sobrado.
    * Obligaría a quien revise y decida ejecutar el codigo a instalar jenkins y los plugins correspondientes.

### ¿ Como Obtener token para realizar Peticiones ? 
  
   ##### Nota:  Lista Usuarios (Hardcode)
      * usuario: jangeles -> password: arenxt123lab
      * usuario: dangeles -> password: arenxt123lab
      * usuario: cangeles -> password: arenxt123lab
      * usuario: fangeles -> password: arenxt123lab
   
   #### Ejecutar el API /login (Metodo POST) 
   
    
    *Ejemplo: http://192.0.0.3:8204/login?nombreUsuario=jangeles&contrasena=arenxt123lab*
    #### El API retornará el valor del token a usar en el header de respuesta: Authorization
    #### A partir de este momento, el token deberá ser colocado en el header: Authorization de las peticiones
    
