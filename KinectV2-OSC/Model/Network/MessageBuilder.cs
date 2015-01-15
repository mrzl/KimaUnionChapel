using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Kinect;
using Rug.Osc;

namespace KinectV2OSC.Model.Network
{
    public class MessageBuilder
    {
        public OscMessage BuildJointMessageX(Body body, KeyValuePair<JointType, Joint> joint)
        {
            var address = String.Format("/bodies/{0}/joints/{1}/x", body.TrackingId, joint.Key );
            var position = joint.Value.Position;
            //System.Diagnostics.Debug.WriteLine(address);
            return new OscMessage(address, position.X);
        }

        public OscMessage BuildJointMessageY(Body body, KeyValuePair<JointType, Joint> joint)
        {
            var address = String.Format("/bodies/{0}/joints/{1}/y", body.TrackingId, joint.Key);
            var position = joint.Value.Position;
            //System.Diagnostics.Debug.WriteLine(address);
            return new OscMessage(address, position.Y);
        }

        public OscMessage BuildJointMessageZ(Body body, KeyValuePair<JointType, Joint> joint)
        {
            var address = String.Format("/bodies/{0}/joints/{1}/z", body.TrackingId, joint.Key);
            var position = joint.Value.Position;
            //System.Diagnostics.Debug.WriteLine(address);
            return new OscMessage(address, position.Z);
        }

        public OscMessage BuildHandMessage(Body body, string key, HandState state, TrackingConfidence confidence)
        {
            var address = String.Format("/bodies/{0}/hands/{1}", body.TrackingId, key);
            //System.Diagnostics.Debug.WriteLine(address);
            return new OscMessage(address, state.ToString(), confidence.ToString());
        }
    }
}
