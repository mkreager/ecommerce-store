# Ecommerce Store
Java EE web MVC project for authentication, order and payment functions of an ecommerce store.
Web tier controllers communicate with external backend web services though JSON/REST using Jersey JAX-RS.

### Controllers
1. Account - for creation of new customer accounts
2. Order - for converting shopping carts into orders
3. Payment - for payment processing
4. Login - for authentication of users
5. Logout - for ending sessions

### Filters
1. Authentication - restricts access to certain paths that require authentication
2. Session - redirects authenticated users away from "login" and "sign up" pages
