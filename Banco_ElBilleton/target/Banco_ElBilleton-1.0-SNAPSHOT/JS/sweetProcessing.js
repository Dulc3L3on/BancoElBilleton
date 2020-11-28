/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


let timerInterval

function advertencia(){
    Swal.fire({
        title: 'Procesing Request',  
        timer: 1500,
        timerProgressBar: true,
        willOpen: () => {
            Swal.showLoading()
                timerInterval = setInterval(() => {
                const content = Swal.getContent()
                if (content) {
                    const b = content.querySelector('b')
                    if (b) {
                        b.textContent = Swal.getTimerLeft()
                    }
                }
                }, 100)
        },
        willClose: () => {
            clearInterval(timerInterval)
        }
    })//será reemplazado por el hilo xD así que apresúrateeee! xD    
}


