package kuit3.backend.controller;

import kuit3.backend.common.exception.RestaurantException;
import kuit3.backend.common.response.BaseResponse;
import kuit3.backend.dao.MenuDao;
import kuit3.backend.dto.menu.GetMenuResponse;
import kuit3.backend.dto.menu.PostMenuRequest;
import kuit3.backend.dto.menu.PostMenuResponse;
import kuit3.backend.dto.restaurant.GetCategoryResponse;
import kuit3.backend.dto.restaurant.GetStoreResponse;
import kuit3.backend.service.MenuService;
import kuit3.backend.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.INVALID_RESTAURANT_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final MenuDao menuDao;

    @RequestMapping("")
    public BaseResponse<List<GetStoreResponse>> getStores(@RequestParam(required = false, defaultValue = "0") Integer lastpageId) {
        log.info("[RestaurantController.getStores]");
        return new BaseResponse<>(restaurantService.getAllStores(lastpageId));
    }
    @RequestMapping("/sortedstores")
    public BaseResponse<List<GetStoreResponse>> getStoresSortedByMinimumPrice(@RequestParam(required = true) Integer minimumPrice) {
        log.info("[RestaurantController.getStoresSortedByMinimumPrice]");
        return new BaseResponse<>(restaurantService.getStoresSortedByMinimumPrice(minimumPrice));
    }
    @RequestMapping("/categories")
    public BaseResponse<List<GetCategoryResponse>> getCategories() {
        log.info("[RestaurantController.getCategories]");
        return new BaseResponse<>(restaurantService.getRestaurants());
    }

    @RequestMapping("/categories/{categoryId}")
    public BaseResponse<List<GetStoreResponse>> getSpecificCategories(@PathVariable("categoryId") long categoryId) {
        log.info("[RestaurantController.getSpecificCategories]");
        return new BaseResponse<>(restaurantService.getSpecificCategories(categoryId));
    }

    // Menu api
    @GetMapping("/{restaurantId}/menus")
    public BaseResponse<List<GetMenuResponse>> getMenus(
            @PathVariable("restaurantId") long restaurantId) {
        log.info("[RestaurantsMenuController.getMenus]");
        if (restaurantId <= 0) {
            throw new RestaurantException(INVALID_RESTAURANT_VALUE);
        }
        return new BaseResponse<>(menuService.getMenus(restaurantId));
    }
    @PostMapping("/{restaurantId}/menus")
    public BaseResponse<PostMenuResponse> addMenu(
            @PathVariable("restaurantId") long restaurantId, @Validated @RequestBody PostMenuRequest postMenuRequest, BindingResult bindingResult) {
        log.info("[RestaurantsMenuController.addMenu]");
        if (restaurantId <= 0) {
            throw new RestaurantException(INVALID_RESTAURANT_VALUE);
        }
        return new BaseResponse<>(menuService.addMenu(postMenuRequest, restaurantId));
    }
}
