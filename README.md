## **1. Introduction**

&nbsp;&nbsp;&nbsp;&nbsp;**1.1 Project information**<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•	Project name: Me&Be Online Shop<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•	Software type: Web application.

&nbsp;&nbsp;&nbsp;&nbsp;**1.2 Project overview**<br>
&nbsp;&nbsp;&nbsp;&nbsp;The Me&Be Online Shop project aims to create a comprehensive and user-friendly e-commerce platform dedicated to providing a wide range of products for mothers and babies. Our goal is to become the go-to online destination for parents seeking high-quality milk, diapers, baby essentials, and maternity wear. The website will cater to both registered members and guest shoppers, offering a seamless shopping experience and exceptional customer service.

&nbsp;&nbsp;&nbsp;&nbsp;**1.3 Purpose**<br>
&nbsp;&nbsp;&nbsp;&nbsp;The purpose of the Me&Be Online Shop project is to create an innovative and user-centric e-commerce platform that caters specifically to the needs of mothers and babies. Our mission is to provide a comprehensive, convenient, and trustworthy shopping experience where parents can find a diverse selection of high-quality products including milk, diapers, baby essentials, and maternity wear.

&nbsp;&nbsp;&nbsp;&nbsp;**1.4 Project Scope**<br>
&nbsp;&nbsp;&nbsp;&nbsp;• Design and Development: Creation of the website, including front-end and back-end development, ensuring a responsive and user-friendly interface.<br>
&nbsp;&nbsp;&nbsp;&nbsp;• User Roles and Permissions: Define and implement roles for Admin, Staff, Member, and Guest, with specific permissions for each.<br>
&nbsp;&nbsp;&nbsp;&nbsp;• Payment Integration: Integration with major payment gateways to facilitate secure and efficient transactions.<br>
&nbsp;&nbsp;&nbsp;&nbsp;• Security Features: Implement SSL encryption, secure login mechanisms, and compliance with PCI DSS standards for payment security.<br>
&nbsp;&nbsp;&nbsp;&nbsp;• Customer Engagement Features: Develop features such as personalized product recommendations, member-exclusive deals, and a newsletter subscription.<br>
&nbsp;&nbsp;&nbsp;&nbsp;• Testing and Quality Assurance: Conduct thorough testing, including functional, performance, and security testing, to ensure the website meets all requirements and standards.<br>
&nbsp;&nbsp;&nbsp;&nbsp;• Deployment and Maintenance: Deploy the website on a reliable hosting platform and provide ongoing maintenance and support.

&nbsp;&nbsp;&nbsp;&nbsp;**1.5 Definitions, Acronyms, and Abbreviations**<br>
&nbsp;&nbsp;&nbsp;&nbsp;• Admin: System administrator with full control over the website.<br>
&nbsp;&nbsp;&nbsp;&nbsp;• Staff: Employees who manage products and orders.<br>
&nbsp;&nbsp;&nbsp;&nbsp;• Member: Registered customers who can make purchases and track orders.<br>
&nbsp;&nbsp;&nbsp;&nbsp;• Guest: Unregistered visitors who can browse products and make purchases.

&nbsp;&nbsp;&nbsp;&nbsp;**1.6 Member in project**
   |        Name         |       Role      |   Student ID   |      Sprint 1      |      Sprint 2      |      Sprint 3      |
   |---------------------|-----------------|----------------|--------------------|--------------------|--------------------|
   |Đặng Lê Hoàng Phúc   |      Leader     |    SE171826    |      T1 - T6.1     |                    |                    |
   |Huỳnh Minh Phước     |      Member     |    SE171830    |      T6.2 - T10    |                    |                    |
   |Hứa Đình Thuận       |      Member     |    SE171787    |      T1 - T14.2    |                    |                    |
   |Nguyễn Chí Thành     |      Member     |    SE171820    |      T15 - T20     |                    |                    |
   |Nguyễn Trường Phi Vũ |      Member     |    SE17xxxx    |     T21 - T25.2    |                    |                    |
   |Lâm Xuân Thái        |      Member     |    SE17xxxx    |     T26 - T37.3    |                    |                    |
   
## **2. User Object**

The project have 4 role: Admin, Staff, Member, Guest
| Functionality              | Admin         | Staff         | Member        | Guest         |
|----------------------------|---------------|---------------|---------------|---------------|
| Add Product                | X             | X             |               |               |
| Edit Product               | X             | X             |               |               |
| Delete Product             | X             | X             |               |               |
| Add Account                | X             | X             |               |               |
| Edit Account               | X             | X             |               |               |
| Delete Account             | X             | X             |               |               |
| Change Password            | X             | X             | X             |               |
| Login & Logout             | X             | X             | X             |               |
| Edit Order Status          | X             | X             |               |               |
| Add Voucher                | X             |               |               |               |
| Edit Voucher               | X             |               |               |               |
| View Product               | X             | X             | X             | X             |
| Add to Cart                | X             | X             | X             | X             |
| Edit Cart                  | X             | X             | X             | X             |
| Remove from Cart           | X             | X             | X             | X             |
| View Cart                  | X             | X             | X             | X             |
| Place Order                | X             | X             | X             | X             |
| View Order History         | X             | X             | X             |               |
| Apply Voucher at Checkout  | X             | X             | X             | X             |
| Make Payment               | X             | X             | X             | X             |
| View Payment Status        | X             | X             | X             | X             |
| Manage Customer Support    | X             | X             |               |               |
| Respond to Reviews         | X             | X             |               |               |
| Generate Reports           | X             | X             |               |               |
| Update Inventory           | X             | X             |               |               |
| View Sales Reports         | X             |               |               |               |

## **3. Use case**

&nbsp;&nbsp;&nbsp;&nbsp;3.1 Sprint 1: 

**UC-01: Register**<br>
T1 – First name – First name is required<br>
T2 – Last name – Last name is required<br>
T3 - Username – Username is required<br>
T4.1 - Email – Email is required<br>
T4.2 – Email – Email must be valid format<br>
T5 – Password - Password is required<br>
T6.1 – Confirm password is required<br>
T6.2 - Confirm password - Confirmation password must match the password<br>
T7 – Birthdate - Birthdate is required<br>
T8.1 – Phone – Phone is required<br>
T8.2 – Phone – Phone cannot have character<br>
**UC-02: Edit Account**<br>
T9 – First name - First name is required<br>
T10 - Last name - Last name is required<br>
T11 – Username – Username is required<br>
T12 – Password - Password is required<br>
T13.1 – Email - Email is required<br>
T13.2 - Email – Email must be valid format<br>
T14.1 - Phone – Phone is required<br>
T14.2 – Phone – Phone cannot have character<br>
**UC-03: Delete Account**<br>
T15- Confirm Delete – Confirm action delete account<br>
**UC-04: View Account**<br>
T16 – View Account – Admin can view list account<br> 
**UC-05: Log-in**<br>
T17 – Email – Login with valid email<br>
T18 – Email – Email is required<br>
T19.1 – Password – Login with correct password<br>
T19.2 – Password – Password is required<br>
**UC-06: Log-out**<br>
T20 – Logout – Logout successfully<br>
**UC-07: New Product**<br>
T21 – Product Name – Product Name is required<br>
T22 – Image – Image format must valid<br>
T23.1 – Price – Product price is required<br>
T23.2 – Price – Product price cannot have character<br>
T24.1 – Sale Price - Sale Price is required<br>
T24.2 - Sale Price - Sale Price cannot have character<br>
T25.1 – Status – Status is required<br>
T25.2 - Status – Status cannot have number<br>
**UC-08: Edit Product**<br> 
T26 – Product Name – Product Name is required<br>
T27 – Image – Image format must valid<br>
T28.1 – Price – Product price is required<br>
T28.2 – Price – Product price cannot have character<br>
T29.1 - Sale Price - Sale Price is required<br>
T29.1 - Sale Price - Sale Price cannot have character<br>
T30.1 - Status – Status is required<br>
T30.2 - Status – Status cannot have number<br>
**UC-09: Delete Product**<br> 
T31 – Confirm Delete – Confirm action delete<br> 
**UC-10: Checkout**<br>
T32 – Customer Name – Customer Name is required<br>
T33.1 – Email – Customer Email is required<br>
T33.2 - Email - Customer Email must valid format<br> 
T34 – Address - Address is required<br>
T35.1 – Phone – Phone is required<br>
T36.2 – Phone - Phone must not have character<br>
T37.3 – Phone – Phone cannot have space<br>






  


