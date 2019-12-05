# PDF FILE FOR THIS ASSIGNMENT IS IN MAIN FOLDER

import random as r

n = 8000000
a = 1000000
p = 8000051 # prime > n

rand_a = r.randint(0, 2**32) * -1   # rand int a for hash function
rand_b = r.randint(0,p-1)   # rand int b for hash function less than p
table = [0] * n # initialize table size

for i in range(a): # hash email address to hash table
    email_address = i
    hash_val = (((rand_a * email_address + rand_b) % p) % n)  # hash function
    table[hash_val] = 1

emails_have_been_hashed = True

for x in range(a): # make sure trusted email addresses are in table
    email_address = x
    hash_val = (((rand_a * email_address + rand_b) % p) % n)   # hash function
    if table[hash_val] != 1:
        print("Q1: Not all emails have been hashed correctly")
        emails_have_been_hashed = False

if emails_have_been_hashed == True: # if a trusted email wasn't let through this will be false
    print("Q1: All emails have been hashed correctly")

# If all trusted emails hash to one that we print out that all the emails can be correctly checked
# if not then it prints all emails have not been correctly printed, it doesn't matter if multiple
# address hash to the same address


print()

spam_got_through = 0
total_emails = 0
for k in range(100 * a):
    email_address = k
    hash_val = (((rand_a * email_address + rand_b) % p) % n)    # hash function
    total_emails +=1
    if email_address > a and table[hash_val] == 1:  # if email out of range of trusted emails and hashes to
        spam_got_through +=1                        # a bucket with val == 1 we count it as spam

probability = spam_got_through / total_emails
print("Q2: actual probability:",probability)

# the "actual probability" is dependent upon our randomly generated variables rand_a & rand_b so there is a
# decent amount of variance from our theoretical value



rand_spam_count = 0
for i in range(1000):
    email_address = r.randint(a, 2**32) # random email/int between a and 2^32
    hash_val = (((rand_a * email_address + rand_b) % p) % n)
    if table[hash_val] == 1:
        rand_spam_count += 1
print()
print("Q3:", rand_spam_count/1000, "proportion of emails got through")

# our theoretical probability tends towards our 'actual probability' that we calculated in q2
# if we run this for values much larger than 1000 we see that the two proportions are quite similar.
# if we run this many times changing a & b each time we will see convergence towards our theoretical value
# which is (~0.118).
