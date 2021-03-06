﻿# Morse-Code-Decoder

An electrical telegraph is a device used to transmit long-distance messages via electrical signals, or bits (0 or 1). One of the most popular encodings used in electrical telegraph is the Morse code, invented by Samuel F. B. Morse: it uses dots, dashes, and spaces to encode the Latin alphabet as well as some special phrases (etc. SOS). However, when communicating via electrical telegraph, the Morse code is not sent in its form of dots and dashes, but is further encoded in a string of bits: 0 would represent space, 1 would represent dot or dash, and we determine whether it's a dot or dash based on how long the length of consecutive 1s is.

Our program aims to decode the Morse code from its bit representation, and then decode the actual message from the Morse code. Given the Morse code, it's relatively easy to translate it to words based on the established international standard (see here: https://en.wikipedia.org/wiki/Morse_code). However, it is much harder to decode the Morse code from its bit representation. The international Morse code standard specifies that in a bit transmission, there must be a "time unit" through which the dots, dashes and spaces will be determined (see Appendix 1). Each message will have a different "time unit" depending on the speed of the person who transmit it.

The difficulty lies in the fact that transmitters are not machines, and therefore aren't likely to follow the rule exactly: one dot might be a little bit shorter than a time unit, while a dash might be a little longer than 3 time units. Our program takes into account this scenario, and uses the method of k-means clustering (see: https://en.wikipedia.org/wiki/K-means_clustering) to filter out various imperfections during transmission. The implementation details is further discussed in Appendix 2.

Appendix 1: Standard for transmission
- A "dot" is 1 time unit long.
- A "dash" is 3 time units long.
- A pause between dots and dashes in a character is 1 time unit long.
- A pause between characters inside a word is 3 time units long.
- A pause between words is 7 time units long.

For example, the message: "HEY JUDE" is "···· · −·−−   ·−−− ··− −·· ·", and if the "time unit" in the bit transmission is 2, the message is: "1100110011001100000011000000111111001100111111001111110000000000000011001111110011111100111111000000110011001111110000001111110011001100000011".

Appendix 2: We will group the runs of 1s and 0s into three groups: the one that is 1 time unit long, 3 time units long and 7 time units long. We also assume that if a run of length m is in a certain group, then all other runs of length m is also in that group. When confusion arises as to whether a particular run is a dot or a dash, we will assume it's a dash (for example, the run "11111" will be interpreted as a dot, while the run "101111" will be a dot and a dash).

