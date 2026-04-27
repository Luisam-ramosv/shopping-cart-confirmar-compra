package isi.shoppingCart.usecases.services;

import isi.shoppingCart.entities.Cart;
import isi.shoppingCart.usecases.dto.OperationResult;
import isi.shoppingCart.usecases.ports.CartRepository;

public class EliminarProductoDelCarritoUseCase {
    private CartRepository cartRepository;

    public EliminarProductoDelCarritoUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public OperationResult execute(int productId) {
        Cart cart = cartRepository.getCart();

        if (cart == null || cart.getItems().isEmpty()) {
            return OperationResult.fail("El carrito está vacío.");
        }

        boolean removed = cart.removeItem(productId);

        if (!removed) {
            return OperationResult.fail("El producto no se encuentra en el carrito.");
        }

        cartRepository.save(cart);

        if (cart.getItems().isEmpty()) {
            return OperationResult.ok("Producto eliminado. El carrito quedó vacío; Total: $ 0.0");
        }

        return OperationResult.ok("Producto eliminado del carrito. Total: $ " + cart.getTotal());
    }
}
