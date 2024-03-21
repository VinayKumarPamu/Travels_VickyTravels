import React, { useState } from 'react';
import axios from 'axios';

function Registration() {
  const [username, setUsername]=useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole]= useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const validateForm = () => {
    //Email Regular Expression
  var pwd_expression = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-])/;
  var letters = /^[A-Za-z]+$/;
  var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    // Add your validation logic here
    if (!username || !email || !password || !confirmPassword || !role) {
      setError('All fields are required.');
      return false;
    }

    if (password !== confirmPassword) {
      setError('Passwords do not match.');
      return false;
    }
    if(username=='')
    {
    alert('Enter your name');
    return false;
    }
    else if(!letters.test(username))
    {
    alert('Name Must be Alphabets');
    return false;
    }
    if(email=='')
  {
  alert('Enter Valid Mail id');
  return false;
  }
  else if (!filter.test(email))
  {
  alert('Invalid email');
  return false;
  }
  if(role=='')
  {
  alert('Enter Valid User Name.');
  return false;
  }
  else if(!letters.test(role))
  {
  alert('Name Must be Alphabets');
  return false;
  }
  if(password=='')
  {
  alert('Enter Valid Password');
  return false;
  }
  else if(!pwd_expression.test(password))
  {
  alert ('Use Mixed Characters for Password');
  return false;
  }
  else if(document.getElementById("t4").value.length < 6)
  {
  alert ('Password min. Length is 6');
  return false;
  }
  else if(document.getElementById("t4").value.length > 12)
  {
  alert ('Password maximum Length is 12');
  return false;
  }
 
  console.log('Form submitted successfully');
      alert('Thank You for Logging In');
      console.log('Redirecting to: ' + redirectToURL);
      window.location.href = redirectToURL;
    return true;
  };

  {				                            
     
      
  }
  console.log('Data to send:', JSON.stringify({ username, email, password, role }));
  const handleSubmit = async (event) => {
    validateForm;
    event.preventDefault();
  
    try {
      const response= await axios.post(
        'http://localhost:8081/auth/signup',
        {
          username,
          email,
          password,
          role,
        },
        {
          headers: {
            'Content-Type': 'application/json', // Set JSON content type
          },
        }
      );
      console.log(JSON.stringify(response)+" "+username);
      alert("Registration Completed");
      window.location.href='http://localhost:3000';
      // ... handle successful response
    } catch (error) {
      // ... handle errors
    }
  };
  const clearFunc = () => {
    console.log("Clearing entered data");
    setUsername(''); // Clear each state value individually
    setEmail('');
    setPassword('');
    setConfirmPassword('');
    setRole('');
  };

  return (
    <div class="reg">
    <h2>REGISTRATION</h2>
<form onSubmit={handleSubmit}  id="registration">
    <div class="Name">
    <h4>Full Name:</h4>
    <input htmlFor="username" type="text" value={username} class="Name"
    onChange={(e)=>setUsername(e.target.value)} placeholder="Full Name" id="t1" required />
    </div><div class="email">
    <h4>Email:</h4>
    <input htmlFor="email" type="email" id="t2" value={email}
        onChange={(e) => setEmail(e.target.value)} required />
    </div>
    <div class="role">
    <h4>Role:</h4>
    <input htmlFor="role" type="role" id="t3" value={role.toUpperCase()}
        onChange={(e) => setRole(e.target.value.toUpperCase())} required />
    </div>
    <div class="Password">
    <h4>Password:</h4>
    <input htmlFor="password" 
        type="password" 
        value={password}
        onChange={(e) => setPassword(e.target.value)}  placeholder="*********" id="t4" required />
    </div><div class="ConfirmPassword">
    <h4>Confirm Password:</h4>
    <input type="password" 
    htmlFor="password"  
    value={confirmPassword}
    onChange={(e) => setConfirmPassword(e.target.value)} placeholder="Confirm Password" id="t5" required />
    </div>
    <div>
    <input type="submit" value="submit" class="sub"  />
    <input type="reset" value="reset" class="cancel" onClick={clearFunc}/>
    </div>
    </form>
    </div>
  );
}
export default Registration;