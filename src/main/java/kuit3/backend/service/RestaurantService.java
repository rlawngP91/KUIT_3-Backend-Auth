package kuit3.backend.service;

import kuit3.backend.dao.RestaurantDao;
import kuit3.backend.dto.restaurant.GetCategoryResponse;
import kuit3.backend.dto.restaurant.GetStoreResponse;
import kuit3.backend.dto.user.GetUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantDao restaurantDao;

    public List<GetStoreResponse> getAllStores(long lastpageId) {
        log.info("[RestaurantService.getAllStores]");
        return restaurantDao.getAllStores(lastpageId);
    }
    public List<GetStoreResponse> getStoresSortedByMinimumPrice(long minimumPrice) {
        log.info("[RestaurantService.getStoresSortedByMinimumPrice]");
        return restaurantDao.getStoresSortedByMinimumPrice(minimumPrice);
    }
    public List<GetCategoryResponse> getRestaurants() {
        log.info("[RestaurantService.getRestaurants]");
        return restaurantDao.getCategories();
    }
    public List<GetStoreResponse> getSpecificCategories(long categoryId) {
        log.info("[RestaurantService.getSpecificCategories]");
        return restaurantDao.getCategoryStores(categoryId);
    }
}
