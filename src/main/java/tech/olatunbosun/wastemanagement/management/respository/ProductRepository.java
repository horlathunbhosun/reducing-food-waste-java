package tech.olatunbosun.wastemanagement.management.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.olatunbosun.wastemanagement.management.models.Product;

/**
 * @author olulodeolatunbosun
 * @created 12/03/2024/03/2024 - 13:20
 */
public interface ProductRepository extends JpaRepository<Product,  Integer> {
}
