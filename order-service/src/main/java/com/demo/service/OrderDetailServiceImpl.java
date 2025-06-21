package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

//import com.demo.configuration.JwtRequestFilter;
import com.demo.dao.OrderDetailDao;
import com.dto.entity.*;
import com.demo.entity.OrderDetail;
import com.demo.entity.OrderInput;
import com.demo.entity.OrderProductQuantity;

@Service
public class OrderDetailServiceImpl implements OrderDetailsService{
	
	private static final String ORDER_PLACED = "Placed";

//    private static final String KEY = "rzp_test_AXBzvN2fkD4ESK";
//    private static final String KEY_SECRET = "bsZmiVD7p1GMo6hAWiy4SHSH";
//    private static final String CURRENCY = "INR";

    @Autowired
    private OrderDetailDao orderDetailDao;
    
    @Autowired
	private AuthServiceFeignClient authservicefeignclient;
	@Autowired
	private ProductServiceFeignClient productservicefeignclient;
	@Autowired
	private CartServiceFeignClient cartservicefeignclient;
	

    public List<OrderDetailDto> getAllOrderDetails(String status) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        if(status.equals("All")) {
            orderDetailDao.findAll().forEach(
                    x -> orderDetails.add(x)
            );
        } else {
            orderDetailDao.findByOrderStatus(status).forEach(
                    x -> orderDetails.add(x)
            );
        }
        List<OrderDetailDto> orderlistdto = new ArrayList<>();
        for(OrderDetail i: orderDetails) {
        	ProductDto product = productservicefeignclient.getProductDetailsById(i.getProductId());
        	UserDto user = authservicefeignclient.getUserDetailsById(i.getUserName());
        	OrderDetailDto obj = new OrderDetailDto(i.getOrderId(),i.getOrderFullName(),i.getOrderFullOrder(),
        			i.getOrderContactNumber(),i.getOrderAlternateContactNumber(),i.getOrderStatus(),
        			i.getOrderAmount(),product, user);
        	orderlistdto.add(obj);
        }
        return orderlistdto;
         
    }

    
    public List<OrderDetailDto> getOrderDetails() {
//        String currentUser = authservicefeignclient.jwtgetcurrentuser();
//        String currentUser = JwtRequestFilter.CURRENT_USER;
    	String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = authservicefeignclient.getUserDetailsById(username);
        List<OrderDetail> orderlist =  orderDetailDao.findByUserName(user.getUserName());
        List<OrderDetailDto> orderlistdto = new ArrayList<>();
        for(OrderDetail i: orderlist) {
        	ProductDto product = productservicefeignclient.getProductDetailsById(i.getProductId());
        	OrderDetailDto obj = new OrderDetailDto(i.getOrderId(),i.getOrderFullName(),i.getOrderFullOrder(),
        			i.getOrderContactNumber(),i.getOrderAlternateContactNumber(),i.getOrderStatus(),
        			i.getOrderAmount(),product, user);
        	orderlistdto.add(obj);
        }
        return orderlistdto;
    }

    
    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o: productQuantityList) {
            ProductDto product = productservicefeignclient.getProductDetailsById(o.getProductId());

//            String currentUser = authservicefeignclient.jwtgetcurrentuser();
//            String currentUser = JwtRequestFilter.CURRENT_USER;
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDto user = authservicefeignclient.getUserDetailsById(username);

            OrderDetail orderDetail = new OrderDetail(
                  orderInput.getFullName(),
                  orderInput.getFullAddress(),
                  orderInput.getContactNumber(),
                  orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductDiscountedPrice() * o.getQuantity(),
                    product.getProductId(),
                    user.getUserName()
//                    orderInput.getTransactionId()
            );
            
            // empty the cart.
            if(!isSingleProductCheckout) {
//                List<CartDto> carts = cartDao.findByUser(user);
                List<CartDto> carts = cartservicefeignclient.getCartbyusername(username);
//                for(int i=0; i<carts.size(); i++) {
//                	System.out.println(i);
//                }
                
//                for(CartDto i: carts){
//                	cartservicefeignclient.deleteCartDetails(i.getCartId());
//                }
                carts.stream().forEach(x -> cartservicefeignclient.deleteCartDetails(x.getCartId()));
                
            }
            
            orderDetailDao.save(orderDetail);
        }
    }

    
    public void markOrderAsDelivered(Integer orderId) {
        OrderDetail orderDetail = orderDetailDao.findById(orderId).get();

        if(orderDetail != null) {
            orderDetail.setOrderStatus("Delivered");
            orderDetailDao.save(orderDetail);
        }

    }

//    public TransactionDetails createTransaction(Double amount) {
//        try {
//
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("amount", (amount * 100) );
//            jsonObject.put("currency", CURRENCY);
//
//            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);
//
//            Order order = razorpayClient.orders.create(jsonObject);
//
//            TransactionDetails transactionDetails =  prepareTransactionDetails(order);
//            return transactionDetails;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
//
//    private TransactionDetails prepareTransactionDetails(Order order) {
//        String orderId = order.get("id");
//        String currency = order.get("currency");
//        Integer amount = order.get("amount");
//
//        TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, KEY);
//        return transactionDetails;
//    }
}
