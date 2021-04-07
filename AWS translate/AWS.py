#######pip install boto3==1.15.1 --user
import boto3
# Method: init_session
# Inputs: region_name:text,access_key:text,secret_access_key:text 
# Output: aws_session:session
def init_session(region_name, access_key, secret_access_key ):
    try:
        session = boto3.Session(region_name=region_name, 
         aws_access_key_id=access_key,
         aws_secret_access_key= secret_access_key)
        return session
    except:
        return 'error in connection establishing'
# Method: translate_string
# Inputs: valid_session:session,input_string:text,from_language_code:text,to_language_code:text
# Output: translated_string:text
def translate_string(valid_session, input_string, source_language_code,target_language_code ):
    try:
        translate = valid_session.client(service_name='translate')
        result = translate.translate_text(Text=input_string, SourceLanguageCode=source_language_code, TargetLanguageCode=target_language_code)         
        return result.get('TranslatedText')
    except:
        return 'translation failed'
# Method: read_image_file_to_bytes
# Inputs: file_path:text
# Output: image_bytes:text
def read_image_file_to_bytes(file_path):
    try:
        ##print("aaa " + file_path)
        ##with open(file_path, 'rb') as source_image:
            ##source_bytes = source_image.read()
        import requests
        source_bytes = requests.get(file_path, allow_redirects=True)
        return source_bytes.content
    except:
        return 'image read failed'
# Method: PPE_face_cover_detection
# Inputs: valid_session:session,image_bytes:text,confidence:num
# Output: detection_result:text
def PPE_face_cover_detection(valid_session, image_bytes,confidence):
    try:
        client = valid_session.client(service_name='rekognition')
        response = client.detect_protective_equipment(Image={'Bytes': image_bytes},  SummarizationAttributes={
        'MinConfidence': confidence,
        'RequiredEquipmentTypes': ['FACE_COVER' ] #['FACE_COVER' | 'HAND_COVER' | 'HEAD_COVER']
        })
        result=""
        for person in response["Persons"]:
            for bp in person["BodyParts"]:
                if bp["Name"] ==  "FACE":
                    result=result+"------>Face detected:"
                    if len(bp["EquipmentDetections"]):                
                        for eq in bp["EquipmentDetections"]:
                            if(eq["Type"]== "FACE_COVER" and eq["Confidence"]>confidence):
                                result=result+"Mask in on the face"
                                if len(eq["CoversBodyPart"]):
                                    proper=eq["CoversBodyPart"]
                                    if(proper["Value"]== True and proper["Confidence"] > confidence):
                                        result=result+" and face is properly covered with the face mask"
                                    else:
                                        result=result+" and face is not properly covered with the face mask"
                            else:
                                result=result+"Mask is not on the face"
                    else:
                        result=result+"Mask is not on the face" 
        if (result==""):
            return "face is not detected in the provided image"
        else:
            return result
    except:
        return 'detection process failed'