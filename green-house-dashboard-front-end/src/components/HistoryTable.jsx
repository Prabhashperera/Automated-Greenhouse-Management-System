import React from 'react';
import { Zap, Fan, Flame, AlertCircle } from 'lucide-react';

const HistoryTable = ({ history }) => {
  // Helper to format the timestamp
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' });
  };

  // Helper to pick the right icon and color based on the action
  const getActionStyle = (action) => {
    if (action.includes('FAN')) return { icon: <Fan className="w-4 h-4 mr-2" />, color: 'text-blue-600 bg-blue-50 border-blue-200' };
    if (action.includes('HEATER')) return { icon: <Flame className="w-4 h-4 mr-2" />, color: 'text-orange-600 bg-orange-50 border-orange-200' };
    return { icon: <Zap className="w-4 h-4 mr-2" />, color: 'text-purple-600 bg-purple-50 border-purple-200' };
  };

  return (
    <div className="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden mt-8">
      <div className="px-6 py-4 border-b border-gray-100 bg-gray-50 flex items-center">
        <AlertCircle className="w-5 h-5 text-gray-500 mr-2" />
        <h3 className="text-lg font-bold text-gray-800">Recent Automated Actions</h3>
      </div>
      
      {history.length === 0 ? (
        <div className="p-8 text-center text-gray-500">
          No automated actions triggered yet. System is stable.
        </div>
      ) : (
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="bg-white text-gray-400 text-xs uppercase tracking-wider">
                <th className="px-6 py-4 font-medium">Time</th>
                <th className="px-6 py-4 font-medium">Zone</th>
                <th className="px-6 py-4 font-medium">Trigger Value</th>
                <th className="px-6 py-4 font-medium">Action Taken</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-50">
              {history.map((log) => {
                const style = getActionStyle(log.action);
                return (
                  <tr key={log.id} className="hover:bg-gray-50 transition-colors">
                    <td className="px-6 py-4 text-sm text-gray-600 font-medium whitespace-nowrap">
                      {formatDate(log.timestamp)}
                    </td>
                    <td className="px-6 py-4 text-sm text-gray-800 font-bold whitespace-nowrap">
                      {log.zoneName}
                    </td>
                    <td className="px-6 py-4 text-sm text-red-500 font-semibold whitespace-nowrap">
                      {log.capturedValue}°C
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`inline-flex items-center px-3 py-1 rounded-full text-xs font-bold border ${style.color}`}>
                        {style.icon}
                        {log.action}
                      </span>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default HistoryTable;