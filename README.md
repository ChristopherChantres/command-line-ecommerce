# Command Line E-commerce System

A Java-based command line e-commerce application that simulates an online store with separate interfaces for buyers and sellers.

## Overview

OnlineDeal is a command line application that simulates a complete e-commerce system. The application supports two types of users - buyers and sellers - and provides different functionalities for each. Buyers can browse products, add them to their cart, make purchases, and manage their accounts. Sellers can add new products, view their inventory, track sales, and manage their accounts.

## Contributors

This project was developed mainly by:
- **Viktor Chargory** - Core functionality and files/buyer/seller APIs
- **Christopher Chantres** - Interface design and reusability/signup/login features

## Features

### For Buyers:
- Create and manage user accounts
- Browse available products
- Add/remove products from shopping cart
- Make purchases with account balance
- View order history
- Return purchased items
- Manage account balance

### For Sellers:
- Create and manage seller accounts
- Add new products to the marketplace
- View products being sold
- Track sales
- Delete products
- Manage account information


## How to Use

### First Steps:
1. When you start the application, you'll be prompted to select your user type:
   - Comprador (Buyer)
   - Vendedor (Seller)

2. You can then create a new account or log in with an existing one.

### For Buyers:
- **Browse Products**: View all available products in the marketplace
- **Add to Cart**: Add desired products to your shopping cart
- **View Cart**: See all items in your cart and modify quantities
- **Checkout**: Complete purchases using your account balance
- **Account Management**: Change password, deposit funds, view order history
- **Return Items**: Return previously purchased items for a refund

### For Sellers:
- **Add Products**: Add new products to the marketplace
- **View My Products**: See all your products currently listed
- **View My Sales**: Track your completed sales
- **Account Management**: Change password, delete account, manage inventory

## Data Persistence

User accounts, products, and order history are persisted across sessions in text files:
- `ArchivoCompradores.txt`: Buyer accounts
- `ArchivoVendedores.txt`: Seller accounts
- `ArchivoProductos.txt`: Product listings
- `ArchivoCompras.txt`: Purchase history

## License

This project is for educational purposes only and is not licensed for commercial use.

---

© 2025 OnlineDeal Command Line E-commerce System