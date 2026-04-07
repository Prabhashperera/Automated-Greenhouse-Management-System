import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Leaf, ChevronRight, Package } from 'lucide-react';

const CropInventory = () => {
  const [crops, setCrops] = useState([]);

  useEffect(() => { fetchCrops(); }, []);

  const fetchCrops = async () => {
    const res = await axios.get('http://localhost:8080/crop-service/api/crops');
    setCrops(res.data);
  };

  const advanceStatus = async (id, currentStatus) => {
    let nextStatus = currentStatus === 'SEEDLING' ? 'VEGETATIVE' : 'HARVESTED';
    await axios.put(`http://localhost:8080/crop-service/api/crops/${id}/status?status=${nextStatus}`);
    fetchCrops();
  };

  return (
    <div className="bg-white rounded-2xl shadow-sm border border-gray-100 p-6 mt-8">
      <div className="flex items-center space-x-2 mb-6">
        <Package className="text-green-600 w-6 h-6" />
        <h2 className="text-xl font-bold text-gray-800">Crop Inventory Lifecycle</h2>
      </div>

      <div className="grid grid-cols-1 gap-4">
        {crops.map(crop => (
          <div key={crop.id} className="flex items-center justify-between p-4 bg-gray-50 rounded-xl border border-gray-100">
            <div>
              <p className="font-bold text-gray-800">{crop.cropType}</p>
              <p className="text-xs text-gray-500">Quantity: {crop.quantity} units</p>
            </div>
            
            <div className="flex items-center space-x-4">
              <span className={`px-3 py-1 rounded-full text-xs font-bold ${
                crop.status === 'HARVESTED' ? 'bg-purple-100 text-purple-700' : 'bg-green-100 text-green-700'
              }`}>
                {crop.status}
              </span>
              
              {crop.status !== 'HARVESTED' && (
                <button 
                  onClick={() => advanceStatus(crop.id, crop.status)}
                  className="p-2 hover:bg-white rounded-full transition-colors text-gray-400 hover:text-green-600 border border-transparent hover:border-gray-100"
                >
                  <ChevronRight className="w-5 h-5" />
                </button>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CropInventory;