import React, { useEffect , useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import "./profile.css";// Locate EROR with css
import { jwtDecode } from 'jwt-decode';

function profile(){
    const [firstName, setFirstName]=useState('');
    const [lastName,setLastName]=useState('');
    const [mobileNumber,setMobileNumber]=useState('');
    const [createdAt,setCreatedAt]=useState('-');
    const [updatedAt,setUpdatedAt]=useState('-');
    const [bio,setBio]=useState('');
    const [loading, setLoading] = useState(true);    

    useEffect(() => {
        // This useEffect will run when the component mounts
        // You can fetch the profile data here
        fetchProfileData();
      },[]); // The empty dependency array ensures that this useEffect only runs once when the component mounts
    const fetchProfileData = async () => {
        let response=null;
        try {
            const authToken = localStorage.getItem('accessToken');
            console.log(authToken);
            const decodedToken = jwtDecode(authToken);
            const userRole = decodedToken.role;
            console.log(userRole);
            if (userRole === "USER") {
                response=await axios.get('http://localhost:8081/user/getProfile',{
                    headers: {
                        Authorization: `Bearer ${authToken}`,
                    },
                });
            }else if (userRole === "ADMIN") {
                response=await axios.get('http://localhost:8081/admin/getProfile',{
                    headers: {
                        Authorization: `Bearer ${authToken}`,
                    },
                });
            }
            
            const { data } = response;
            if (!data) {
              setLoading(false);
              return;
            }
            
            setBio(data.bio || "-");
            setFirstName(data.firstName || "-");
            setLastName(data.lastName || "-");
            setMobileNumber(data.mobileNumber || "-");
            const [year, month, day, hour, minute, second] = data.created_At;
            setCreatedAt(new Date(year, month - 1, day, hour, minute, second).toLocaleString());
            const [updatedAtYear, updatedAtMonth, updatedAtDay, updatedAtHour, updatedAtMinute, updatedAtSecond] = data.updated_At;
            setUpdatedAt(new Date(updatedAtYear, updatedAtMonth - 1, updatedAtDay, updatedAtHour, updatedAtMinute, updatedAtSecond).toLocaleString());
            setLoading(false);
        } catch (error) {
      // Handle errors, e.g., log them or display a user-friendly message
      console.error('Error fetching profile data:', error.message);
      setLoading(false);
      
    }
    };
    return(
      <div className="home">
      <Link to={"/home"} className="link"><button id='home'>Back to home</button></Link>
      <div className="profile">
          {loading ? (
              <p>Loading...</p>
          ) : (
              <>
                  <table>
                      <tbody>
                          <tr>
                              <td><h3>First Name</h3></td>
                              <td><h4>:</h4></td>
                              <td><p>{firstName}</p></td>
                          </tr>
                          <tr>
                              <td><h3>Last Name</h3></td>
                              <td><h4>:</h4></td>
                              <td><p>{lastName}</p></td>
                          </tr>
                          <tr>
                              <td><h3>Mobile Number</h3></td>
                              <td><h4>:</h4></td>
                              <td><p>{mobileNumber}</p></td>
                          </tr>
                          <tr>
                              <td><h3>Created At</h3></td>
                              <td><h4>:</h4></td>
                              <td><p>{createdAt}</p></td>
                          </tr>
                          <tr>
                              <td><h3>Updated At</h3></td>
                              <td><h4>:</h4></td>
                              <td><p>{updatedAt}</p></td>
                          </tr>
                          <tr>
                              <td><h3>Bio</h3></td>
                              <td><h4>:</h4></td>
                              <td><p>{bio}</p></td>
                          </tr>
                      </tbody>
                  </table>
                  {!firstName && (
                      <Link to={"/updateProfile"} className="link">
                          <button>Create Profile</button>
                      </Link>
                  )}
                  {firstName && (
                      <Link to={"/updateProfile"} className="link">
                          <button>Update Profile</button>
                      </Link>
                  )}
              </>
          )}
      </div>
  </div>
    );
}
export default profile;