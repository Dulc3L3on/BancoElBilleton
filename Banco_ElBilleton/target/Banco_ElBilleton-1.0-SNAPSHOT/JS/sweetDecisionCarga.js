/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const swalWithBootstrapButtons = Swal.mixin({
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
})
