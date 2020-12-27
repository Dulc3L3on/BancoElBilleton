/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


switch(document.getElementById("tipoMsje").value){
    case "creacionCuentaExitoso":
         Swal.fire({
            title:'Creación Exitosa',            
            text: 'La cuenta ha sido agregada\n\
                   de forma exitosa al cliente :)',            
            position: 'center',
            icon: 'success',
            backdrop: false,
            timer: 2700
        })            
    break;    
    
    case "bienvenida":
       Swal.fire({
            title: 'Bienvenido Usuario',
            showClass: {
                popup: 'animate__animated animate__fadeInDown'
            },
            hideClass: {
                popup: 'animate__animated animate__fadeOutUp'
            }
        })//recuerda que no colocaré un msje para los trabajadores en la esquna supe derecha, por el hecho de que se mostraría tapnado a los íconos, puesto que la posición solo puede ser en lugares ya establecidos, no por medio de numeritos... [cuando termines TODO xD, tendrás derechoa mejorar la apriencia de los componentes ya listados y buscar cómo lograr esto para el msje de bienvenida de los trabjadores xD] recuerda TODO =  terminar todas las funciones, serciorarse que todas las excepciones tratadas funcionen como lo programaste [o si lo puedes mejorar que bien xD... aunque así está bien xD], verifacar y tratar [o arreglar xD, espero que no xD] las que no tomaste en cuenta y luego de eso puedes superEnbellecer la página xD
    break; 

    case "envioExitoso":    
        Swal.fire({
            title:'Envío exitoso',            
            text: 'la solicitud se ha enviado correctamente',            
            position: 'center',
            icon: 'success',
            timer: 2700
        })           
    break;//Creo que es así el nombre dle ícono xD
    
    case "solicitudAceptada":
          Swal.fire({
            toast: true,
            backdrop: true,
            text: 'Ya existe una asociación concretada\n\
                   con el número de cuenta ingresado',                                    
            position: 'center',
            icon: 'info'            
        })
    break;      
    
    case "intentosAgotados":
          Swal.fire({
            toast: true,
            backdrop: true,
            title: 'Intentos de envío Agotados',
            text: 'No esposible enviar una solicitud\n\
                   más al número de cuenta ingresado',            
            position: 'center',
            icon: 'error'
        })
    break;//es que no es error, pero no creo que pdebiera colocar un icono de "warning"...
    
    case "sinReaccion":
          Swal.fire({            
            toast: true,
            backdrop: true,
            text: 'Una solicitud enviada con anterioridad\n\
                   a la misma cuenta aún no ha sido respondida\n\
                   por favor espera la respuesta...',            
            position: 'center',
            icon: 'info'
        })
    break;
    
    case "cuentaPropia":
          Swal.fire({            
            toast: true,
            backdrop: true,
            text: 'Tus cuentas poseen una asociación\n\
                   entre sí desde que son creadas...',            
            position: 'center',
            icon: 'warning'
        })
    break;
    
    case "sinDatos":
        Swal.fire({
            title:'No existen registros de transacciones\n\
                   para el intervalo de tiempo especificado',
            toast: true,            
            position: 'bottom-end',
            icon: 'warning'
        })
    break;  
    
    case "ningunaTransaccionAtendida":
        Swal.fire({
            title:'No existen registros de transacciones\n\
                   atendidas para el rango de tiempo especificado',
            toast: true,   
            backdrop: false,
            position: 'bottom-end',
            icon: 'warning', 
            timer: 2700
        })
    break; 
    
    case "fueraDeHorario":
         Swal.fire({
            title:'Denegación de Acceso',            
            text: 'Esta función solo puede emplearse\n\
                   dentro del horario de trabajo',            
            background:'#EAEDED',
            backdrop: false,
            position: 'center',
            icon: 'error'            
    }) 
    break;
}
/*Swal.fire({
  position: 'top-end',
  icon: 'success',
  title: 'Your work has been saved',
  showConfirmButton: false,
  timer: 1500
})
 * exactamente esto es lo que querría para el trabajador, pero no sé si se vería bien...
 * 
 */




/*const swalWithBootstrapButtons = Swal.mixin({
  customClass: {
    confirmButton: 'btn btn-success',
    cancelButton: 'btn btn-danger'
  },
  buttonsStyling: false
})

swalWithBootstrapButtons.fire({  
  title: 'Desea continuar?',
  text: "Los registros no cargados,\n\
  perderán el codigo original",
  icon: 'warning',
  backdrop: false,
  allowOutsideClick: false,
  allowEnterKey: false,
  allowEscapeKey: false,
  showCancelButton: true,
  confirmButtonText: 'SÍ',
  cancelButtonText: 'NO',
  reverseButtons: true
}).then((result) => {
  if (result.isConfirmed) {
     window.location.href = "Login.jsp";
  }
})*///esto es para mostrar el msje de que los registros no cargados exitosamente perderá el código que poseían originalmente... [sea #cta, codigo del usuario ó codigoTransacción...]
