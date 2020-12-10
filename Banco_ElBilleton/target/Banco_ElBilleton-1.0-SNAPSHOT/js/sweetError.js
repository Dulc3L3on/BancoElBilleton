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

    case "erroActualizacionUsuario":
        Swal.fire({
            title:'Error en la actualización',            
            text: 'Intente nuevamente por favor',
            backdrop: true,
            position: 'center',
            icon: 'error'
        })
    break;
    
    case "CUIrepetido":
        Swal.fire({
            title:'CUI repetido',            
            text: 'El CUI debe ser único para cada\n\
                   uno de los usuarios',
            backdrop: true,
            position: 'center',
            icon: 'error'
        })
    break;
    
    case "erroCreacionUsuario":
        Swal.fire({
            title:'Error en la creación',            
            text: 'Intente nuevamente por favor',
            backdrop: true,
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
            backdrop: true,
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
            backdrop: true,
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
            backdrop: true,
            position: 'center',
            icon: 'error'
        })            
    break;
}       


   
   














