# Barclays Tech Test

I wish I had more time to put towards this, but, unfortunately both my work and personal life are hectic at the moment but here's the basics of the app:

## Project Setup
The project was built using *JDK 21* and uses Maven as its build tool.

Before you can run the app you will need to set up some environment variables. I personally do this with my run config in my IDE during development, here's what you'll need:

```config
# Database env vars
DB_PASSWORD=the password for your database user
DB_USERNAME=the username for your database user

# Security env vars
JWT_SECRET=some long secret used to generate JWTs
```