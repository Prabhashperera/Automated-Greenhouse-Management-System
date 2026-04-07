import React from 'react';
import { Thermometer, Droplets, Activity } from 'lucide-react';

// Added 'telemetry' to the props
const ZoneCard = ({ zone, telemetry }) => {
  return (
    <div className="bg-white rounded-2xl shadow-sm border border-gray-100 p-6 hover:shadow-lg transition-all duration-300">
      {/* Header */}
      <div className="flex justify-between items-start mb-6">
        <div>
          <h3 className="text-xl font-bold text-gray-800">{zone.name}</h3>
          <p className="text-xs text-gray-400 mt-1">ID: {zone.deviceId}</p>
        </div>
        <span className="bg-green-100 text-green-700 text-xs font-bold px-3 py-1 rounded-full">
          Active
        </span>
      </div>

      {/* Sensor Data (Now Dynamic) */}
      <div className="grid grid-cols-2 gap-4 mb-6">
        <div className="flex items-center space-x-3 bg-gray-50 p-3 rounded-xl">
          <div className="bg-orange-100 p-2 rounded-lg">
            <Thermometer className="w-5 h-5 text-orange-600" />
          </div>
          <div>
            <p className="text-xs text-gray-500 font-medium">Temperature</p>
            {/* Display telemetry data if it exists, otherwise show -- */}
            <p className="text-lg font-bold text-gray-800">
              {telemetry ? `${telemetry.value.temperature}°C` : '-- °C'}
            </p>
          </div>
        </div>

        <div className="flex items-center space-x-3 bg-gray-50 p-3 rounded-xl">
          <div className="bg-blue-100 p-2 rounded-lg">
            <Droplets className="w-5 h-5 text-blue-600" />
          </div>
          <div>
            <p className="text-xs text-gray-500 font-medium">Humidity</p>
            {/* Display telemetry data if it exists, otherwise show -- */}
            <p className="text-lg font-bold text-gray-800">
              {telemetry ? `${telemetry.value.humidity}%` : '-- %'}
            </p>
          </div>
        </div>
      </div>

      {/* Threshold Limits */}
      <div className="border-t border-gray-100 pt-4 flex items-center justify-between">
        <div className="flex items-center text-sm text-gray-500">
          <Activity className="w-4 h-4 mr-1 text-gray-400" />
          <span>Limits: <span className="font-semibold text-gray-700">{zone.minTemp}°C</span> to <span className="font-semibold text-gray-700">{zone.maxTemp}°C</span></span>
        </div>
      </div>
    </div>
  );
};

export default ZoneCard;