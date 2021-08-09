// 'Captura' de elementos del DOM
let cards = document.getElementsByClassName("card");

/**
 * Ref: https://developer.mozilla.org/en-US/docs/Web/API/Window/DOMContentLoaded_event
 * Se ejecuta una vez que el DOM se cargue completamente
 */
 document.addEventListener('DOMContentLoaded',() => {
    init();
});


/**
 * Agrega y captura el evento 'click' de un boton
 */
const init = () => {
    for (const card of cards) {
        card.addEventListener("click", e => {
            // console.log('target', e.target);
            //Verifico que el evento sea del <button id="cartButton"></button>
            if(e.target.id === 'cartButton') {
                agregarItem(card);
            }
            e.stopPropagation();
        });
    }
}


/**
 * Agrega al carrito el elemento seleccionado
 * @param {*} e HTML Element
 */
 const agregarItem = obj => {
    let auxCart = JSON.parse(sessionStorage.getItem('cart'));
    let cart = auxCart? auxCart : [];
    // console.log('Elemento : ', obj);

    let id = obj.querySelector('button#cartButton').dataset.id;
    //Objeto auxiliar
    let aux = { 
        id,
        titulo: obj.querySelector("h5.card-title").textContent,
        precio: obj.querySelector('span#precio').textContent,
        cantidad: 1
    }

    let index = cart.findIndex( item => item.id == id);

    if(index === -1) {
        cart.push(aux);
    } else {
        cart[index].cantidad+=1;
    }
    persistirCarrito(cart);
}


/**
 * Guarda la informacion del carrito en Session
 * @param {Array} cart 
 */
const persistirCarrito = cart => {
    sessionStorage.setItem('cart', JSON.stringify(cart));
}