import React, { useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  console.log(email,password);

  const handleSubmit = async (event) => {
    event.preventDefault();
  
    try {
      console.log('Sending request...');
      const response=await axios.post(
        'http://localhost:8081/auth/signIn',
        {
          email,
          password,
        },
        {
          headers: {
            'Content-Type': 'application/json', // Set JSON content type
          },
        }
      );
      console.log('Received response:', response.data);
      
      // Token Storage
      const expirationTime = new Date().getTime() + 24 * 60 * 60 * 1000; // 24 hours in milliseconds
      // Store token and expiration time in localStorage
      localStorage.removeItem("accessToken");
      localStorage.removeItem("tokenExpiration");
      localStorage.setItem("accessToken", response.data);
      localStorage.setItem("tokenExpiration", expirationTime.toString());
      window.location.href = 'http://localhost:3000/home';
      } 
      catch (error) {
      console.error('Error in handleSubmit:', error);
  if (error.response) {
    console.log('Backend responded with:', error.response.data);
    console.log('Response status:', error.response.status);
    console.log('Response headers:', error.response.headers);
  } else {
    console.error('Error making the request:', error.message);
  }
    }
  };
  console.log('Data to send:', JSON.stringify({ email, password }));
  // ... (form fields and submission logic below)

  return (
    <div class="container1-login">
        <form class="login-box" onSubmit={handleSubmit}>
            <div class="head">
                <h2>Login</h2>
            </div>
            <div class="login">
                <div class="form-control1">
                    <input htmlFor="email" type="email" id="email" value={email}
        onChange={(e) => setEmail(e.target.value)} placeholder="Valid User Name" class="userName" required />
                </div>
                <div class="form-control1">
                    <input htmlFor="password" 
                    type="password" 
                    id="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}  placeholder="*********" class="pWD" required />
                </div>
                <div class="form-control1">
                    <input type="submit"  value="Login Now" class="btn"/>
                    <a href="#" class="link">
                    <button class="fgt">forgot password</button></a>
                </div>
            </div>
        </form>
        <Link to="/new_reg"class="link" >
        <button id="newReg"> New User Regstration</button>
        </Link>
    </div>
  );
}
export default Login;