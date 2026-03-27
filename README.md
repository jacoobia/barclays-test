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

## Databasing

I realistically wouldn't use any kind of document based DB for a bank project like this and would lean more relational (I tend to favour Postgres, but who doesn't? lol) but for speed of prototyping mongo is undefeated,
and I haven't enabled virtualisation for my computer since I did a clean install of windows recently so docker wont work hahahaha

## Auth

The project uses JWTs to secure the endpoints, a JWT access and refresh token can be provisioned upon sign in and access tokens can be refreshed with valid refresh tokens.
Normally in a real project I would be returning these tokens in different ways, i.e the refresh token would live as a HTTP only cookie and I would also store refresh tokens
against accounts in the database in case I needed to revoke them but in the absence of time I'm omitting that and it's something to talk about during the chat.

I also took heavy inspiration from myself 10 months ago when making the interceptor for the auth, I like the easier dev experience approach for prototyping things and of course
it could be improved significantly, for one I wouldn't really put the ownership on token auth on the apps themselves, rather I'd have something at the gateway level to reject as far left/as early as possible.

This is a private org I made for one of my personal projects, but this is the auth lib mentioned above, happy to run through it on the call if interested
https://github.com/LumbridgeGuide/auth-library/tree/main