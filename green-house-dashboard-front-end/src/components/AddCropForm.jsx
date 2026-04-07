import React, { useState } from 'react';
import axios from 'axios';
import { PlusCircle, Sprout } from 'lucide-react';

const AddCropForm = ({ onCropAdded }) => {
  const [formData, setFormData] = useState({ cropType: '', quantity: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // POST to the API Gateway
      await axios.post('http://localhost:8080/crop-service/api/crops', formData);
      setFormData({ cropType: '', quantity: '' });
      onCropAdded(); // Refresh the list
      alert("New crop batch registered!");
    } catch (err) {
      console.error(err);
      alert("Error adding crop. Is the service on 8084 and Gateway on 8080?");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 mb-8 max-w-2xl">
      <h3 className="text-lg font-bold text-gray-800 mb-4 flex items-center">
        <PlusCircle className="w-5 h-5 mr-2 text-green-600" />
        Register New Crop Batch
      </h3>
      <div className="grid grid-cols-2 gap-4">
        <input 
          type="text" placeholder="Crop Type (e.g. Tomato)" 
          className="border p-2 rounded-lg text-sm focus:ring-2 focus:ring-green-500 outline-none"
          value={formData.cropType}
          onChange={(e) => setFormData({...formData, cropType: e.target.value})}
          required
        />
        <input 
          type="number" placeholder="Quantity" 
          className="border p-2 rounded-lg text-sm focus:ring-2 focus:ring-green-500 outline-none"
          value={formData.quantity}
          onChange={(e) => setFormData({...formData, quantity: e.target.value})}
          required
        />
      </div>
      <button type="submit" className="mt-4 bg-green-600 text-white px-4 py-2 rounded-lg text-sm font-bold hover:bg-green-700 transition-colors flex items-center">
        <Sprout className="w-4 h-4 mr-2" /> Add to Inventory
      </button>
    </form>
  );
};

export default AddCropForm;