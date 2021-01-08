/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


switch(document.getElementById("tipoMsje").value){//se ejecutará al nada más cargar el documento [html] que es justamente lo que necesito, puesto que los valores que comparo para ver si cumple con la condi la situación las obtengo del servlet
   case "errorLog":
        Swal.fire({
            title:'Usuario o contraseña incorrectos',
            toast: true,
            backdrop: true,
            position: 'bottom-end',
            icon: 'error', 
            timer: 1700
        })
    break;    

    case "errorActualizacionUsuario":
        Swal.fire({
            title:'Error en la actualización',            
            text: 'Intente nuevamente por favor',
            backdrop: false,
            position: 'center',
            icon: 'error'
        })
    break;
    
    case "CUIrepetido":
        Swal.fire({
            title:'CUI repetido',            
            text: 'El CUI debe ser único para cada\n\
                   uno de los usuarios',
            backdrop: false,
            position: 'center',
            icon: 'error'
        })
    break;
    
    case "erroCreacionUsuario":
        Swal.fire({
            title:'Error en la creación',            
            text: 'Intente nuevamente por favor',
            backdrop: false,
            position: 'center',
            icon: 'error'
        })
    break;
    
    case "errorTransaccion":
        Swal.fire({
            title:'Error de Transacción',            
            text: 'Ha surgido un imprevisto por el cual\n\
                   no fue posible realizar la transacción\n\
                   por favor intente nuevamente',
            backdrop: true,
            position: 'center',
            icon: 'error'
        })
    break;
    
    case "errorBusquedaUsuario":
        Swal.fire({
            title:'Error de Búsqueda',            
            text: 'No se logró encontrar los\n\
                   datos del usuario, por favor\n\
                   intente nuevamente',
            backdrop: true,
            position: 'center',
            icon: 'error'
        })
    break;
    
    case "errorBusquedaCuenta":
        Swal.fire({
            title:'Error de Búsqueda',            
            text: 'No existe una cuenta con el \n\
                   número ingresado',
            backdrop: true,
            position: 'center',
            icon: 'error'
        })
    break;
    
     case "errorBusquedaDueno":
        Swal.fire({
            title:'Error de Búsqueda',            
            text: 'No se hallaron los datos\n\
                   del cliente correspondiente',
            backdrop: false,
            position: 'center',
            icon: 'error'
        })
    break;
    
    case "errorBusquedaCuentas":
        Swal.fire({
            title:'Error de Búsqueda',            
            text: 'No se halló el listado de\n\
                   cuentas pertenecientes al cliente',
            backdrop: true,
            position: 'center',
            icon: 'error'
        })
    break;
    
    case "errorEnvio":
         Swal.fire({
            title:'Fallo de envío',            
            text: 'Surgió un error al enviar la solicitud\n\
                   por favor intente de nuevo',
            backdrop: false,
            position: 'center',
            icon: 'error'
        })    
    break;     
    
    case "errorBusquedaSolicitudes":
         Swal.fire({
            title:'Error búsqueda de solicitudes',            
            text: 'Surgió un error al realizar la\n\
                   búsqueda, por favor intente de nuevo',
            backdrop: false,
            position: 'center',
            icon: 'error'
        })    
    break;    
    
    case "errorActualizacionEstadoSolicitud":
        Swal.fire({
            title:'Error en la actualización',            
            text: 'Surgió un error que impidió\n\
                   registrar la reacción hacia la\n\
                   solicitud recibida, por favor\n\
                   intente nuevamente',
            backdrop: false,
            position: 'center',
            icon: 'error'
        })
    break;

    case "errorCreacionCuenta":
        Swal.fire({
            title:'Cuenta No Creada',            
            text: 'Surgió un error que impidió\n\
                   la creación de la cuenta, \n\
                   por favor intente nuevamente',            
            position: 'center',
            icon: 'error'            
        })            
    break;
    
    case "errorEnvioMail":
        Swal.fire({
            title:'Correo no enviado',            
            text: 'Surgió un error que impidió\n\
                   el envío del correo, \n\
                   por favor intente nuevamente',            
            position: 'center',
            icon: 'error'            
        })            
    break;
    
    case "errorTransferencia":
        Swal.fire({
            title:'Transferencia fallida',           
             text:'Surgió un error que impidió\n\
                   la transferencia se realizara, \n\
                   por favor intente nuevamente',           
            position: 'center',
            icon: 'error'            
        })            
    break;
    
     case "errorBusquedaTransacciones":
        Swal.fire({
            title:'Error de Búsqueda',           
             text:'Surgió un error que impidió la\n\
                   búsqueda exitosa de las transacciones, \n\
                   por favor intente nuevamente',           
            position: 'center',
            icon: 'error'            
        })            
    break;
    
    case "errorBusqueda":
        Swal.fire({
            title:'Error de Búsqueda',
            text: 'Surgió algo que impidió hallar\n\
                   los datos a mostrar por e reporte',
            toast: true,            
            position: 'bottom-end',
            icon: 'warning'
        })
    break;  
}    