<template>
  <div>
    <form class="login" @submit.prevent="login">
      <h1>Sign in</h1>
      <label>User name</label>
      <input required v-model="username" type="text" placeholder="Snoopy"/>
      <label>Password</label>
      <input required v-model="password" type="password" placeholder="Password"/>
      <hr/>
      <button type="submit" v-on:click="submit">Login</button>
    </form>
  </div>
</template>

<script>
  import auth from '../auth';

  export default {
    name: "Login",
    data: () => ({
      username: null,
      password: null
    }),
    methods: {
      submit: function () {
        auth.login(this.username, this.password, loggedIn => {
          if (!loggedIn) {
            this.$snackbar.open("User is not logged in. Try again.");
          } else {
            this.$router.replace(this.$route.query.redirect || '/')
          }
        });
      }
    }
  }
</script>
