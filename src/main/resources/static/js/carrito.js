// 'Captura' de elementos del DOM
let tBodyElement = document.getElementById("cartItems");
let templateItems = document.getElementById("template-items").content;

let tFoot = document.getElementById("footer");
let templateFooterTable = document.getElementById('template-footer').content;

let fragment = new DocumentFragment();

/**
 * Se ejecuta una vez que el DOM se cargue completamente
 */
document.addEventListener('DOMContentLoaded',() => {
    mostrarItems();
});

tBodyElement.addEventListener("click", e => {
    btnAcciones(e);
});


let getCarrito = () => {
    let carrito = JSON.parse(sessionStorage.getItem('cart'));
    return ( carrito != null)? carrito : [];
}

let setCarrito = (carrito) => {
    sessionStorage.setItem('cart', JSON.stringify(carrito));
}

/**
 * Rellena con los datos del carrito tbody mediante templates y fragments
 */
const mostrarItems = () => {
    let carrito = getCarrito();
    tBodyElement.innerHTML = ``;
    
    carrito.forEach( producto => {
        templateItems.querySelector("th").textContent = producto.id;
        templateItems.querySelectorAll('td')[0].textContent = producto.titulo
        templateItems.querySelectorAll('td')[1].textContent = producto.cantidad;
        templateItems.querySelector('span').textContent = producto.precio * producto.cantidad;
        //botones
        templateItems.querySelector('.btn-info').dataset.id = producto.id;
        templateItems.querySelector('.btn-danger').dataset.id = producto.id;

        const clone = templateItems.cloneNode(true);
        fragment.appendChild(clone);
    });
    
    tBodyElement.appendChild(fragment);

    mostrarFooter(carrito);
}


const mostrarFooter = carrito => {
    tFoot.innerHTML = ``;

    const totalCantidad = carrito.reduce( (acc,producto) => producto.cantidad+acc, 0);
    const totalPrecio = carrito.reduce( (acc,producto) => acc+(producto.cantidad*producto.precio), 0);

    templateFooterTable.querySelectorAll('td')[0].textContent = totalCantidad;
    templateFooterTable.querySelector('span').textContent = totalPrecio;

    const clone = templateFooterTable.cloneNode(true);
    fragment.appendChild(clone);

    tFoot.appendChild(fragment);


    let btnClearCart = document.getElementById("limpiarCarrito");

    btnClearCart.addEventListener('click', () => {
        setCarrito([]);
        mostrarItems();
    });
}

const btnAcciones = event => {
    let carrito = getCarrito();

    // Aumento la cantidad
    if(event.target.classList.contains('btn-info')) {
        let id = event.target.dataset.id;
        let index = carrito.findIndex((producto)=> producto.id === id);

        carrito[index].cantidad = carrito[index].cantidad +1;

        setCarrito(carrito);
        mostrarItems();
    }

    //Disminuye la cantidad
    if(event.target.classList.contains('btn-danger')) {
        let id = event.target.dataset.id;
        let index = carrito.findIndex((producto)=> producto.id === id);

        carrito[index].cantidad = carrito[index].cantidad -1;

        if(carrito[index].cantidad === 0) {
            carrito.splice(index,1);
        }
        setCarrito(carrito);
        mostrarItems();
    }

    event.stopPropagation();
}