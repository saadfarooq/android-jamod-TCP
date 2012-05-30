/***
 * Copyright 2002-2010 jamod development team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***/

package net.wimpi.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.procimg.IllegalAddressException;
import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.procimg.ProcessImage;

/**
 * Class implementing a <tt>ReadDeviceIdentificationRequest</tt>.
 * The implementation directly correlates with the Encapsulted Inteface
 * Transport function (FC 43) with MEI type 14. It
 * encapsulates the corresponding request message.
 *
 * @author Dieter Wimberger
 * @version @version@ (@date@)
 */
public final class ReadDeviceIdentificationRequest
    extends ModbusRequest {

	  //instance attributes
	  private int m_MEIType = 14;
	  private int m_DeviceId = 1;
	  private int m_ObjectId = 0;
	
	  /**
	   * Constructs a new <tt>ReadDeviceIdentificationRequest</tt>
	   * instance.
	   */
	  public ReadDeviceIdentificationRequest() {
		  super();
		  setFunctionCode(Modbus.READ_DEVICE_IDENTIFICATION);
		  // 3 bytes (unit id and function code is excluded)
		  setDataLength(3);
	  }
	  
	  public ReadDeviceIdentificationRequest(int MEIType, int deviceId, int objectId) {
	    super();
	    setFunctionCode(Modbus.READ_INPUT_REGISTERS);
	    //3 bytes (unit id and function code is excluded)
	    setDataLength(3);
	  }//constructor
	  
	  
	  public ReadDeviceIdentificationRequest(int slaveAddress) {
		  super();
		  setFunctionCode(Modbus.READ_DEVICE_IDENTIFICATION);
		  // 3 bytes (unit id and function code is excluded)
		  setDataLength(3);
		  setUnitID(slaveAddress);
	  }
	
	  public void writeData(DataOutput dout)
	      throws IOException {
		dout.writeByte(m_MEIType);
		dout.writeByte(m_DeviceId);
		dout.writeByte(m_ObjectId);
	  }//writeData
	
	  public void readData(DataInput din)
	      throws IOException {
	    m_MEIType = din.readUnsignedByte();
	    m_DeviceId = din.readUnsignedByte();
	    m_ObjectId = din.readUnsignedByte();
	  }//readData

	/**
	 * Get the value of MEIType
	 * @return the MEIType
	 */
	public int getMEIType() {
		return m_MEIType;
	}

	/**
	 * Set the value for MEIType
	 * @param MEIType the MEIType to set
	 */
	public void setMEIType(int MEIType) {
		this.m_MEIType = MEIType;
	}

	/**
	 * Get the value of DeviceId
	 * @return the DeviceId
	 */
	public int getDeviceId() {
		return m_DeviceId;
	}

	/**
	 * Set the value for DeviceId
	 * @param DeviceId the DeviceId to set
	 */
	public void setDeviceId(int DeviceId) {
		this.m_DeviceId = DeviceId;
	}

	/**
	 * Get the value of ObjectId
	 * @return the ObjectId
	 */
	public int getObjectId() {
		return m_ObjectId;
	}

	/**
	 * Set the value for ObjectId
	 * @param ObjectId the ObjectId to set
	 */
	public void setObjectId(int ObjectId) {
		this.m_ObjectId = ObjectId;
	}
	  
	  
	  

}//class ReadInputRegistersRequest
