import React, { useState } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

function updateProfile(){
    const [firstName, setFirstName]=useState('');
    const [lastName,setLastName]=useState('');
    const [mobileNumber,setMobileNumber]=useState('');
    const [bio,setBio]=useState('');

    let response=null;

    const fetchProfileData = async (event) => {
        event.preventDefault();
        try {
            const authToken = localStorage.getItem('accessToken');
            console.log(authToken);
            const decodedToken = jwtDecode(authToken);
            const userRole = decodedToken.role;
            console.log(userRole);

            if (!firstName || !lastName || !mobileNumber) {
                window.alert('Please fill in all required fields');
                throw new Error('Please fill in all required fields.');
            }
            if (userRole === "USER") {
            response=await axios.post('http://localhost:8081/user/profile',
            {
                firstName,
                lastName,
                mobileNumber,
                bio,
            },
            {  
            headers: {
                Authorization: `Bearer ${authToken}`,
            },
        });
            }else if (userRole === "ADMIN") {
            response=await axios.post('http://localhost:8081/admin/profile',
            {
                firstName,
                lastName,
                mobileNumber,
                bio,
            },
            {  
            headers: {
                Authorization: `Bearer ${authToken}`,
            },
        });
        }
        
        console.log(response.data.createdAt);
        window.alert("Profile details are Updated");
        window.location.href = 'http://localhost:3000/profile';
        
      
    } catch (error) {
      // Handle errors, e.g., log them or display a user-friendly message
      console.error('Error fetching profile data:', error.message);
    }
    };
    return(
        <div class="profileUpdate">
            <form onSubmit={fetchProfileData} >
        <h3>First Name</h3>
        <input htmlFor="firstName" type="text" id="firstName" value={firstName}
        onChange={(e) => setFirstName(e.target.value)} placeholder="First Name"  required />
       
        <h3>Last Name</h3>
        
        <input htmlFor="lastName" type="text" id="lastName" value={lastName}
        onChange={(e) => setLastName(e.target.value)} placeholder="Last Name"  required />
       
        
        <h3>Mobile Number</h3>
        
        <input htmlFor="mobileNumber" type="text" id="mobileNumber" value={mobileNumber}
        onChange={(e) => setMobileNumber(e.target.value)} placeholder="mobileNumber"  required />
    
        <h3>Bio</h3>
        
        <input htmlFor="bio" type="text" id="bio" value={bio}
        onChange={(e) => setBio(e.target.value)} placeholder="Bio" required /><br />

        <input type="submit"  value="Update Profile" class="btn"onClick={fetchProfileData}/>
            </form>
       
    </div>
    );
}
export default updateProfile;