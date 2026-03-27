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

I realistically wouldn't use any kind of document based DB for a bank project like this and would lean more relational, I tend to favour Postgres, but who doesn't?.
Another reason I used MongoDB is because it's effectively schema-less so iteration is quick and easy, need a new field? throw it in. Need to get rid of a field? Drop it from the document class.
This was useful when it came to the auth section as it allowed me to add a password to the user document without having to worry about database migration.

## Auth

The project uses JWTs to secure the endpoints, a JWT access and refresh token can be provisioned upon sign in and access tokens can be refreshed with valid refresh tokens.
Normally in a real project I would be returning these tokens in different ways, i.e the refresh token would live as a HTTP only cookie and I would also store refresh tokens
against accounts in the database in case I needed to revoke them but in the absence of time I'm omitting that and it's something to talk about during the chat.

I also took heavy inspiration from myself 10 months ago when making the interceptor for the auth, I like the easier dev experience approach for prototyping things and of course
it could be improved significantly, for one I wouldn't really put the ownership on token auth on the apps themselves, rather I'd have something at the gateway level to reject as far left/as early as possible.

This is a private org I made for one of my personal projects, but this is the auth lib mentioned above, happy to run through it on the call if interested
https://github.com/LumbridgeGuide/auth-library/tree/main


## Mapping improvements

For POC purposes I have used a `.from()` method pattern on my response DTOs, since separation of DTOs and document models is something I tend to do for security reasons. 
In a real project, I would probably use MapStruct to generate mappers for me during compilation, this does require some plugin changes to the POM build process to not break Lombok but ultimately it's worth it to avoid boilerplate and ensure consistency.

https://mapstruct.org/

## Testing

I would have, given more time added tests for more than just the user service but the user service tests are an example of how I would build tests.
I tend to build tests that are defined first, then I abstract out the data into json files that can be updates as the project evolves since contracts/payloads tend to change.
This approach also affords the opportunity to create new test data and swap out test data as and when needed, case and point currently I use `create-user-0.json` but I could easily switch over to using `create-user-1.json` to 
prove nothing is hard coded in the tests.