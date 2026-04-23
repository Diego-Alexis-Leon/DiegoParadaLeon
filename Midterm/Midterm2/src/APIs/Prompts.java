package APIs;

public class Prompts {
    public static final String pInitialImage ="""
                    Create a single cinematic illustration that represents all the places mentioned in this description.
                    The image must combine the locations into one coherent and visually connected composition.
                    Keep the result clear, detailed, and visually engaging.
                    Do not include labels, captions, or any text in the image.
        
                    Description:
                    """;
    public static final String pFinalImage ="""
                    Create a clean illustrated route map based on the places listed below.

                    Requirements:
                    - Show all places on a single map.
                    - Mark the first place with a clearly distinct start marker.
                    - Mark the last place with a clearly distinct end marker.
                    - Mark intermediate places with numbered markers in order.
                    - Under each marker, write the name of the place.
                    - Make the route visually easy to follow.
                    - Use a simple, readable infographic style.
                    - White or light background.
                    - Do not add unnecessary decorative elements.

                    Ordered places:
                    """;
    public static final String descriveVideo= """
            Analyze this entire video and respond ONLY with valid JSON.

            I want an array called "segments", where each element contains:
            - start_time
            - end_time
            - visual_description
            - location
            - image_prompt
            - audio_narration

            Rules:
            - Divide the video into coherent segments.
            - Do not invent anything that is not visible in the video.
            - image_prompt must be suitable for generating an image related to that segment.
            - audio_narration must be a natural narration that fits the duration of its corresponding segment.
              This means that longer segments should have longer narration, and shorter segments should have shorter narration.
            """;

    public static final String phrase =  """
            Based on the visual description of the places, write ONE motivational phrase.

            Rules:
            - It must be short
            - It must sound inspiring
            - It must be in English
            - Do not use quotation marks
            - Do not add any extra explanation

            Description:
            """;

}
