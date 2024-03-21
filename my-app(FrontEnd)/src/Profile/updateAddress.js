import React, { useState } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

function updateAddress(){
    const [addressLine1, setAddress_line1]=useState('');
    const [addressLine2,setAddress_line2]=useState(null);
    const [city,setCity]=useState('');
    const [country,setCountry]=useState('');
    const [postalCode,setPostal_code]=useState('');
    const [state,setState]=useState('');
    const [primary,setIsPrimary]=useState(false);
    let response=null;

    const fetchProfileData = async (event) => {
        event.preventDefault();
        try {
            const authToken = localStorage.getItem('accessToken');
            console.log(authToken);
            const decodedToken = jwtDecode(authToken);
            const userRole = decodedToken.role;
            console.log(userRole);
            if (primary) {
                setAddress_line2(addressLine1);
            } else {
                setAddress_line2(''); // Set addressLine2 to an empty string if primary is false
            }
            console.log(primary);

            if (!addressLine1 || !city || !country || !postalCode || !state) {
                window.alert('Please fill in all required fields');
                throw new Error('Please fill in all required fields.');
            }
            if (userRole === "USER") {
            response=await axios.post('http://localhost:8081/user/address',
            {
                addressLine1,
                addressLine2,
                city,
                country,
                postalCode,
                state,
                primary,
            },
            {  
            headers: {
                Authorization: `Bearer ${authToken}`,
            },
        });
            }else if (userRole === "ADMIN") {
            response=await axios.post('http://localhost:8081/admin/address',
            {
                addressLine1,
                addressLine2,
                city,
                country,
                postalCode,
                state,
                primary,
            },
            {  
            headers: {
                Authorization: `Bearer ${authToken}`,
            },
        });
        }
       
        console.log(response.data.createdAt);
        window.alert("Address details are Updated");
        window.location.href = 'http://localhost:3000/address';
        
      
    } catch (error) {
      // Handle errors, e.g., log them or display a user-friendly message
      console.error('Error fetching profile data:', error.message);
    }
    };
    return(
        <div class="profileUpdate">
            <form onSubmit={fetchProfileData} >
        <h3>Present Address</h3>
        <input htmlFor="addressLine1" type="text" id="addressLine1" value={addressLine1}
        onChange={(e) => setAddress_line1(e.target.value)} placeholder="Present Address"  required />
       
        <h3>Permenant Address</h3>
        
        <input htmlFor="addressLine2" type="text" id="addressLine2" value={addressLine2}
        onChange={(e) => setAddress_line2(e.target.value)} placeholder="Permenant Address"  required />
        
        <h3>Country</h3>
        
        <input htmlFor="country" type="text" id="country" value={country}
        onChange={(e) => setCountry(e.target.value)} placeholder="country"  required />
    
        <h3>State</h3>
        
        <input htmlFor="state" type="text" id="state" value={state}
        onChange={(e) => setState(e.target.value)} placeholder="state"  required /><br />

        <h3>City</h3>
        
        <input htmlFor="city" type="text" id="city" value={city}
        onChange={(e) => setCity(e.target.value)} placeholder="city"  required /><br />

        <h3>Postal Code</h3>
        
        <input htmlFor="postalCode" type="text" id="postalCode" value={postalCode}
        onChange={(e) => setPostal_code(e.target.value)} placeholder="postal_code"  required /><br />

        <h3>Is Present Address is same as Permenant Address?
        
        <input htmlFor="primary" type="checkbox" id="primary" checked={primary} 
        onChange={(e) => setIsPrimary(e.target.checked)} /><br /></h3>

        <input type="submit"  value="Update Address" class="btn"onClick={fetchProfileData}/>
            </form>
       
    </div>
    );
}
export default updateAddress;